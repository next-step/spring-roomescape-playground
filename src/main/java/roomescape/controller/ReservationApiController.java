package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationNotFoundException;
import roomescape.service.ReservationService;

@RestController // @Controller + @ResponseBody: 특정 클래스가 RESTful 웹 서비스의 컨트롤러 역할을 하도록 지정
public class ReservationApiController {

  private final ReservationService reservationService;

  public ReservationApiController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping("/reservations")
  public ResponseEntity<List<Reservation>> readReservation() {
    return ResponseEntity.ok(reservationService.findAll());
  }

  @PostMapping("/reservations")
  public ResponseEntity<Reservation> createReservation(@Valid @RequestBody
  Reservation reservation, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new IllegalArgumentException(ErrorMessage.INVALID_ARGUMENT.getMessage());
    }
    Reservation newReservation = reservationService.save(reservation);
    return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                         .body(newReservation);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") Long id) {
    try {
      reservationService.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (ReservationNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
