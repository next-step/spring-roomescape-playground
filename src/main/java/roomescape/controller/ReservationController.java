package roomescape.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.domain.Reservation.NotFoundReservationException;
import roomescape.dto.ReservationRequestDto;
import roomescape.service.ReservationService;

@Controller
@RequiredArgsConstructor
public class ReservationController {
  private final ReservationService reservationService;

  @GetMapping("/reservation")
  public String reservation() {
    return "new-reservation";
  }

  @GetMapping("/reservations")
  public ResponseEntity<List<Reservation>> reservations() {
    List<Reservation> reservations = reservationService.getReservations();
    return ResponseEntity.ok(reservations);
  }

  @PostMapping("/reservations")
  public ResponseEntity<Reservation> addReservation(@RequestBody @Validated ReservationRequestDto reservationRequestDto) {
    try {
      Long.parseLong(reservationRequestDto.getTime());
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().build();
    }

    Reservation reservation = reservationService.addReservation(reservationRequestDto);
    return ResponseEntity
        .created(URI.create("/reservations/" + reservation.getId()))
        .body(reservation);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
    reservationService.removeReservation(id);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(NotFoundReservationException.class)
  public ResponseEntity<Void> handleException(NotFoundReservationException e) {
    return ResponseEntity.badRequest().build();
  }
}