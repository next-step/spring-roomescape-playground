package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.request.ReservationRequestDto;
import roomescape.dto.response.ReservationResponseDto;
import roomescape.service.ReservationService;
import roomescape.service.TimeService;

@RequiredArgsConstructor
@Controller
public class ReservationController {

  private final ReservationService reservationService;
  private final TimeService timeService;

  @GetMapping("/reservation")
  public String reservation() {
    return "new-reservation";
  }

  @GetMapping("/reservations")
  public ResponseEntity<?> reservations() {
    List<Reservation> reservations = reservationService.findAll();
    return ResponseEntity.ok().body(ReservationResponseDto.from(reservations));
  }

  @PostMapping("/reservations")
  public ResponseEntity<?> createReservation (@RequestBody @Valid ReservationRequestDto request) {

    Time time = timeService.findById(request.time());
    Reservation reservation = reservationService.create(request.toReservation(time));

    String url = "/reservations/" + reservation.getId();

    return ResponseEntity.created(URI.create(url)).body(ReservationResponseDto.from(reservation));
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> createReservation (@PathVariable Long id) {

    reservationService.delete(id);
    return ResponseEntity.noContent().build();
  }
}