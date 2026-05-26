package roomescape;

import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation.Response> create(@RequestBody Reservation.Request request) {
        log.info("예약 생성 요청: name={}, date={}, timeId={}", request.getName(), request.getDate(), request.getTimeId());

        Reservation.Response response = reservationService.createReservation(request);
        return ResponseEntity.created(URI.create("/reservations/" + response.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Reservation.Response>> readAll() {
        log.info("전체 예약 조회 요청");
        List<Reservation.Response> responseList = reservationService.findAllReservations();
        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("예약 삭제 요청: id={}", id);
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
