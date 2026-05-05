package roomescape;

import java.io.NotActiveException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.exception.BadRequestException;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> showReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        if (reservation.getName() == null || reservation.getName().isBlank()) {
            throw new BadRequestException("이름이 존재하지 않습니다.");
        }

        if (reservation.getDate() == null || reservation.getDate().isBlank()) {
            throw new BadRequestException("날짜가 존재하지 않습니다.");
        }

        if (reservation.getTime() == null || reservation.getTime().isBlank()) {
            throw new BadRequestException("시간이 존재하지 않습니다.");
        }

        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public  ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));

        if (!removed) {
            throw new BadRequestException("예약을 찾을 수 없습니다.");
        }

        return ResponseEntity.noContent().build();
    }
}
