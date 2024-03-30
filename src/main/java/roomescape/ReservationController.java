package roomescape;

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

@Controller
public class ReservationController {
  private final AtomicLong index = new AtomicLong(0);
  private final List<Reservation> reservations = new ArrayList<>();

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/reservation")
  public String reservation(){
    return "reservation";
  }

  @GetMapping("/reservations")
  @ResponseBody
  public ResponseEntity<List<Reservation>> getReservations() {
    return ResponseEntity.ok()
        .body(reservations);
  }

  @PostMapping("/reservations")
  @ResponseBody
  public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
    if (reservation.getName().isEmpty() || reservation.getDate().isEmpty() || reservation.getTime().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    reservation.setId(index.incrementAndGet());
    reservations.add(reservation);
    return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
        .body(reservation);
  }

  @DeleteMapping("/reservations/{id}")
  @ResponseBody
  public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
    if (!reservations.removeIf(reservation -> reservation.getId().equals(id))) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.noContent().build();
  }
}
