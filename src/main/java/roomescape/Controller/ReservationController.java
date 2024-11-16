package roomescape.Controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.DTO.ReservationRequest;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;
import roomescape.Exception.NotFoundReservationException;
import roomescape.Service.ReservationService;

@RestController
public class ReservationController {

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping("/reservations")
  public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {

    if (request.getName() == null || request.getName().isEmpty() ||
        request.getDate() == null || request.getDate().isEmpty() ||
        request.getTime() == null || request.getTime().isEmpty()) {
      throw new NotFoundReservationException("Reservation not found");
    }

    Long timeId;
    try {
      timeId = Long.valueOf(request.getTime());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid time format");
    }

    Time time = new Time(timeId, null);
    Reservation reservation = new Reservation(null, request.getName(), request.getDate(), time);

    Long id = reservationService.addReservationByKey(reservation);

    Reservation savedReservation = reservationService.getReservationByID(id);

    return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId()))
        .body(savedReservation);
  }

  @GetMapping("/reservations")
  public ResponseEntity<List<Reservation>> read() {
    List<Reservation> reservations = reservationService.getAllReservations();

    return ResponseEntity.ok().body(reservations);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {

    List<Reservation> reservations = reservationService.getReservationsByID(id);

    if (reservations.isEmpty()) {
      throw new NotFoundReservationException("Reservation not found");
    }

    reservationService.deleteReservationByID(id);

    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(NotFoundReservationException.class)
  public ResponseEntity<Void> handleException(NotFoundReservationException e) {
    return ResponseEntity.badRequest().build();
  }
}
