package roomescape;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {

    private final DbController dbController;

    public ReservationController(DbController dbController) {
        this.dbController = dbController;
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(dbController.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationRequestDto requestDto) {
        Reservation reservation = new Reservation(
                null,
                requestDto.getName(),
                requestDto.getDate(),
                requestDto.getTime()
        );

        Long id = dbController.insert(reservation);

        Reservation newReservation = Reservation.toEntity(reservation, id);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int deleteNumber = dbController.deleteReservation(id);

        if(deleteNumber == 0) {
            throw new NotFoundReservationException("Reservation not found: id=" + id);
        }

        return ResponseEntity.noContent().build();
    }
}
