package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDAO;
import roomescape.domain.reservation.ReservationRequest;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.reservation.Reservation;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservationDAO.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequest request) {
        Long generatedId = reservationDAO.insertWithKeyHolder(request);
        Reservation newReservation = Reservation.toEntity(request, generatedId);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int deletedCount = reservationDAO.delete(id);
        if (deletedCount == 0) {
            throw new NotFoundReservationException("해당 예약을 찾을 수 없습니다");
        }
        return ResponseEntity.noContent().build();
    }
}
