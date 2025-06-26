package roomescape.controller;

<<<<<<< HEAD
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.dao.Reservations;
import roomescape.domain.dto.ReservationRequest;
=======
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequest;
import roomescape.exception.ReservationNotFoundException;
>>>>>>> {boya-go}/boya-go

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

<<<<<<< HEAD
    private final Reservations reservations;

    public ReservationApiController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations.findAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody ReservationRequest dto) {
        Reservation reservation = reservations.insert(dto);
=======
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(List.copyOf(reservations.values()));
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest dto) {
        Reservation reservation = Reservation.create(dto.getName(), dto.getDate(), dto.getTime());
        reservations.put(reservation.getId(), reservation);
>>>>>>> {boya-go}/boya-go
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
<<<<<<< HEAD
        reservations.delete(id);
=======
        Reservation removed = reservations.remove(id);
        if (removed == null) {
            throw new ReservationNotFoundException("삭제할 수 있는 예약이 존재하지 않습니다.");
        }
>>>>>>> {boya-go}/boya-go
        return ResponseEntity.noContent().build();
    }
}
