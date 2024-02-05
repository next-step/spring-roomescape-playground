package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationDTO;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.value.Date;
import roomescape.value.ID;
import roomescape.value.Name;
import roomescape.value.Time;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/reservation")
    public String reservationPage(Model model) {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationDTO> getReservations() {
        return reservations.stream()
                .map(Reservation::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> addReservation(@RequestBody ReservationDTO reservationDTO) {
        if (reservationDTO.getName() == null || reservationDTO.getDate() == null || reservationDTO.getTime() == null) {
            throw new InvalidReservationException("예약에 필요한 인자값이 비어있어요.");
        }
        Reservation newReservation = Reservation.builder()
                .id(new ID(counter.incrementAndGet()))
                .name(new Name(reservationDTO.getName()))
                .date(new Date(reservationDTO.getDate()))
                .time(new Time(reservationDTO.getTime()))
                .build();
        reservations.add(newReservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getID()))
                .body(newReservation.toDTO());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getID().equals(id));
        if (!removed) {
            throw new NotFoundReservationException("아이디 " + id + "로 예약된 기록을 찾을 수 없어요.");
        }
        return ResponseEntity.noContent().build();
    }
}
