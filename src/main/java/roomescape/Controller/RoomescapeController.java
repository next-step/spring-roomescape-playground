package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class RoomescapeController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index ;

    public RoomescapeController() {
        long maxId = reservations.stream()
                .mapToLong(Reservation::getId)
                .max()
                .orElse(0L);

        index = new AtomicLong(maxId + 1);
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage(Model model) {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }

    // 예약 추가
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation newReservation) {
        if (newReservation.getName() == null || newReservation.getName().trim().isEmpty() ||
                newReservation.getDate() == null || newReservation.getDate().trim().isEmpty() ||
                newReservation.getTime() == null || newReservation.getTime().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Long newId = index.getAndIncrement();
        Reservation reservation = new Reservation(
                newId,
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime()
        );
        reservations.add(reservation);

        URI location = URI.create("/reservations/" + newId);
        return ResponseEntity.created(location).body(reservation);
    }

    // 예약 삭제
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Optional<Reservation> reservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (reservation.isPresent()) {
            reservations.remove(reservation.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
