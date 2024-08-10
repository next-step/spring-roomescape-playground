package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private QueryingDAO queryingDAO;

    @GetMapping
    public ResponseEntity<List<Reservation>> readAll() {
        List<Reservation> reservations = queryingDAO.findAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation request) {
        if (request.getName() == null || request.getName().isBlank() ||
                request.getDate() == null || request.getDate().isBlank() ||
                request.getTime() == null || request.getTime().isBlank()) {
            throw new IllegalArgumentException("데이터가 비어있습니다.");
        }

        queryingDAO.createReservation(request);

        Long newId = queryingDAO.getLastInsertId();
        Reservation newReservation = new Reservation(newId, request.getName(), request.getDate(), request.getTime());
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = queryingDAO.findReservationById(id);
        if (reservation == null) {
            throw new IllegalArgumentException("예약이 존재하지 않습니다.");
        }

        queryingDAO.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
}
