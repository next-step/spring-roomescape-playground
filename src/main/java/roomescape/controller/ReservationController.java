package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

  private final ReservationService reservationService;

  @Autowired
  ReservationController(final ReservationService reservationService) {
    this.reservationService = reservationService;
  }


  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/reservation")
  public String reservation() {
    return "new-reservation";
  }

  @GetMapping("/reservations")
  @ResponseBody
  public List<Reservation> getAllReservations() {
    return reservationService.searchAllReservations();
  }

  @PostMapping("/reservations")
  @ResponseBody
  public ResponseEntity<Reservation> createReservation(@RequestBody @Valid Reservation reservation) {
    Reservation response = reservationService.makeReservation(reservation);
    URI location = URI.create("/reservations/" + response.getId());

    return ResponseEntity.created(location).body(response);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
    reservationService.cancelReservation(id);

    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler({NoSuchElementException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<Void> handleException(Exception e) {
    return ResponseEntity.badRequest().build();
  }
}
