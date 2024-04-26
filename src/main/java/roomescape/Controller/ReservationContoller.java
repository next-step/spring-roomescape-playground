package roomescape.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationDTO;
import roomescape.Domain.Reservation;
import roomescape.Service.ReservationService;

import java.net.URI;
import java.util.List;



@RestController
@RequestMapping(value = "/reservations")
public class ReservationContoller {
  @Autowired
  private ReservationService reservationService;

  @GetMapping
  public List<Reservation> reservations() {
    return reservationService.getAllReservations();
  }

  @PostMapping
  public ResponseEntity<Reservation> postReservation(@Valid @RequestBody ReservationDTO reservationDTO) {

    Reservation newReservation = reservationService.newReservation(reservationDTO);
    return ResponseEntity.created(URI.create("/reservation/" + newReservation.getId())).body(newReservation);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
    if (reservationService.deleteReservation(id)) return ResponseEntity.badRequest().build();
    return ResponseEntity.noContent().build();
  }
}
