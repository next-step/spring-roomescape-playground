package roomescape.reservation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @GetMapping("/reservations")
  public ResponseEntity<List<ReservationResponse>> getReservationInfo() {
    List<ReservationResponse> response = reservationService.getReservationInfo();
    return ResponseEntity.ok(response);
  }

}
