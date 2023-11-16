package roomescape;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReservationController {

  private List<Reservation> reservations = new ArrayList<>();

  private AtomicLong index = new AtomicLong(1);

  @GetMapping("/reservation")
  public String reservation(Model model) throws ParseException {
    return "reservation";
  }

  @PostMapping("/reservations")
  public ResponseEntity<Reservation> create(@RequestBody Reservation reservation){
    Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
    reservations.add(newReservation);

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create("/reservations/" + newReservation.getId()));

    return new ResponseEntity<>(newReservation, headers, HttpStatus.CREATED);
  }

  @GetMapping("/reservations")
  public ResponseEntity<List<Reservation>> read() {
    return ResponseEntity.ok().body(reservations);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    Reservation reservation = reservations.stream()
        .filter(it -> Objects.equals(it.getId(), id))
        .findFirst()
        .orElseThrow(RuntimeException::new);

    reservations.remove(reservation);

    return ResponseEntity.noContent().build();
  }

}
