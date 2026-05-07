package roomescape.controller;

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
import roomescape.DTO.Reservation;
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
        validateDuplicate(reservation);

        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public  ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));

        if (!removed) {
            throw new BadRequestException("예약번호가 " + id + "인 예약은 존재하지 않습니다.");
        }

        return ResponseEntity.noContent().build();
    }

    private void validateDuplicate(Reservation reservation) {
        boolean duplicated = reservations.stream()
                .anyMatch(savedReservation ->
                        savedReservation.isSameSchedule(reservation));

        if (duplicated) {
            throw new BadRequestException("이미 예약된 날짜와 시간입니다.");
        }
    }
}
