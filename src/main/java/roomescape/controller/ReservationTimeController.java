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
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

  private final ReservationTimeService reservationTimeService;

  public ReservationTimeController(ReservationTimeService reservationTimeService) {
    this.reservationTimeService = reservationTimeService;
  }

  @GetMapping
  public ResponseEntity<List<ReservationTime>> readReservationTimes() {
    return ResponseEntity.ok(reservationTimeService.findAll());
  }

  @PostMapping
  public ResponseEntity<ReservationTime> createReservationTime(
      @RequestBody ReservationTimeRequest reservationTimeRequest) {
    ReservationTime newReservationTime =
        reservationTimeService.create(reservationTimeRequest.toDomain(null));
    return ResponseEntity.created(URI.create("/times/" + newReservationTime.getId()))
        .body(newReservationTime);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
    reservationTimeService.delete(id);
    return ResponseEntity.noContent().build();
  }
}