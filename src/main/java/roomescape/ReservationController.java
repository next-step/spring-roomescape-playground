package roomescape;

import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        log.info("예약 생성 요청: name={}, date={}, time={}", reservation.getName(), reservation.getDate(), reservation.getTime());

        Reservation newReservation = reservationService.createReservation(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation.Response>> readAll() {
        log.info("전체 예약 조회 요청");

        List<Reservation.Response> responseList = reservationService.findAllReservations().stream()
                .map(Reservation.Response::new)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("예약 삭제 요청: id={}", id);

        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}