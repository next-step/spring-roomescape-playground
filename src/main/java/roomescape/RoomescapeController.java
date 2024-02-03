package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationDTO;
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
        return "home";
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
        // ID, Name, Date, Time 객체를 생성하여 Reservation 생성자에 전달
        Reservation newReservation = new Reservation(
                new ID((int) counter.incrementAndGet()),
                new Name(reservationDTO.getName()),
                new Date(reservationDTO.getDate()),
                new Time(reservationDTO.getTime())
        );
        reservations.add(newReservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getID()))
                .body(newReservation.toDTO());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("id") long id) {
        reservations.removeIf(reservation -> reservation.getID() == id);
        return ResponseEntity.noContent().build();
    }
}
