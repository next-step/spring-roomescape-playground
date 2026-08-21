package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.dto.ReservationRequest;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

  private final ReservationRepository reservationRepository;

  public ReservationController(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  @GetMapping("")
  public ResponseEntity<List<Reservation>> readReservations() {
    return ResponseEntity.ok(reservationRepository.readReservations());
  }

  @PostMapping("")
  public ResponseEntity<Reservation> createReservation(
      @RequestBody ReservationRequest reservationRequest) {
    Reservation newReservation = reservationRepository.reserve(reservationRequest);
    return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
        .body(newReservation);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
    reservationRepository.delete(id);
    return ResponseEntity.noContent().build();
  }

}
