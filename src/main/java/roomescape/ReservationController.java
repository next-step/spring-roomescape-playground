package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        if (reservation.getName() == null || reservation.getName().isEmpty() ||
                reservation.getDate() == null || reservation.getDate().isEmpty() ||
                reservation.getTime() == null || reservation.getTime().isEmpty()) {
            throw new NotFoundReservationException("Required fields are missing.");
        }

        long newId = index.incrementAndGet();
        reservation.setId(newId);
        reservations.add(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + newId)
                .body(reservation);
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId() == id);
        if (!removed) {
            throw new NotFoundReservationException("Reservation not found.");
        }
        return ResponseEntity.noContent().build();
    }

}