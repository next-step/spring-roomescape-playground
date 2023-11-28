package roomescape.reservation;

import static org.springframework.http.HttpStatus.CREATED;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

  private final ReservationService reservationService;

  @GetMapping
  public ResponseEntity<List<Reservation>> getReservationInfo() {
//    List<ReservationResponse> response = reservationService.getReservationInfo();
    return ResponseEntity.ok(reservationService.getReservationInfo());
  }

  @PostMapping
  public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest request) {
//    ReservationResponse reservation = reservationService.addReservation(request.getDate(), request.getName(),
//        request.getTime());
    Reservation reservation = reservationService.addReservation(request.getName(), request.getDate(), request.getTime());

    return ResponseEntity.status(CREATED)
        .location(URI.create("/reservations/" + reservation.getId())).body(reservation);
  }

//  @GetMapping("/{id}")
//  public ResponseEntity<ReservationResponse> getByReservationId(@PathVariable Long id) {
////    ReservationResponse reservations = reservationService.getByReservationId(id);
//    return ResponseEntity.ok(reservations);
//  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
    reservationService.deleteReservation(id);
    return ResponseEntity.noContent().build();
  }
}

