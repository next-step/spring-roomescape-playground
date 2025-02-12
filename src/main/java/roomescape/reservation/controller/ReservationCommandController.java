package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationDao;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.reservation.dto.ReservationCreateResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ReservationCommandController {

    private final ReservationDao reservationDao;

    public ReservationCommandController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservationList() {
        List<Reservation> reservations = reservationDao.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateResponse> createReservation(@RequestBody ReservationCreateRequest request) throws URISyntaxException {
        Long id = reservationDao.save(Reservation.ofNew(
                request.getName(),
                request.getDate(),
                request.getTime()
        ));

        URI uri = new URI("/reservations/" + id);
        return ResponseEntity
                .created(uri)
                .body(new ReservationCreateResponse(id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") Long id) {
        reservationDao.delete(id);

        return ResponseEntity.noContent().build();
    }
}
