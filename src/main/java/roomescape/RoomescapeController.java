package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationDTO;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.value.Date;
import roomescape.value.ID;
import roomescape.value.Name;
import roomescape.value.Time;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

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
    public ResponseEntity<ReservationDTO> addReservation(@Valid @RequestBody ReservationDTO reservationDTO, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            throw new InvalidReservationException(errorMessage);
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
            throw new NotFoundReservationException("해당 아이디로 예약된 기록을 찾을 수 없어요.");
        }
        return ResponseEntity.noContent().build();
    }
}
