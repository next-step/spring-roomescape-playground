package roomescape.Domains.Reservation;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.exceptions.BadRequestException;

@RestController
public class ReservationApiController {

  private final ReservationService reservationService;

  public ReservationApiController(final ReservationService reservationService) {
    this.reservationService = reservationService;
  }


  @GetMapping("/reservations")
  public ResponseEntity<List<Reservation>> getReservations() {
    List<Reservation> reservations = reservationService.list();

    return ResponseEntity.ok().body(reservations);
  }

  @PostMapping("/reservations")
  public ResponseEntity<Reservation> create(@RequestBody ReservationStoreRequestDto requestDto) {
    if (Objects.isNull(requestDto.date()) || requestDto.date().isEmpty() ||
        Objects.isNull(requestDto.time()) || requestDto.time().isEmpty() ||
        Objects.isNull(requestDto.name()) || requestDto.name().isEmpty()) {
      throw new BadRequestException("올바르지 않은 입력입니다.");
    }
    Reservation reservation = reservationService.create(requestDto);

    return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
        .body(reservation);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    reservationService.deleteByIdOrElseThrowException(id);

    return ResponseEntity.noContent().build();
  }
}
