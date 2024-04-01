package roomescape;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  public ResponseEntity<List<Reservation>> getReservations() {
    return ResponseEntity.ok()
        .body(reservations);
  }

  @PostMapping("/reservations")
  public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) throws BadRequestException {
    if (reservation.getName().isEmpty() || reservation.getDate().isEmpty() || reservation.getTime().isEmpty()) {
      throw new BadRequestException("필수 정보가 누락되었습니다");
    }

    reservation.setId(index.incrementAndGet());
    reservations.add(reservation);
    return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
        .body(reservation);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable Long id) throws BadRequestException {
    if (!reservations.removeIf(reservation -> reservation.getId().equals(id))) {
      throw new BadRequestException("해당하는 예약이 존재하지 않습니다");
    }
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<String> handleException(BadRequestException e) {
    return ResponseEntity.badRequest()
        .body(e.getMessage());
  }

}
