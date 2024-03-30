package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class RoomescapeController {

  private List<Reservation> reservations = new ArrayList<>();
  private AtomicInteger index = new AtomicInteger(1);
  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/reservation")
  public String reservation() {
    return "reservation";
  }

  @GetMapping("/reservations")
  @ResponseBody
  public List<Reservation> reservationList() {
    return reservations;
  }

  @PostMapping("/reservations")
  @ResponseBody
  public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
    Reservation added = Reservation.toEntity(reservation, index.getAndIncrement());
    reservations.add(added);
    return ResponseEntity.created(URI.create("/reservations/" + added.getId())).body(added);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable("id") int id) {
    Reservation willDeleted = reservations.stream()
            .filter(item -> Objects.equals(item.getId(), id))
            .findFirst()
            .orElseThrow(RuntimeException::new);

    reservations.remove(willDeleted);

    return ResponseEntity.noContent().build();
  }
}
