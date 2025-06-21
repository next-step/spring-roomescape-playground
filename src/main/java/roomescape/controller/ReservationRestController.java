package roomescape.controller;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ReservationNotFoundException;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> responses = getSortedReservations().stream()
            .map(ReservationResponse::from)
            .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
        @RequestBody ReservationRequest request) {
        long id = index.getAndIncrement();
        Reservation reservation = new Reservation(id, request.name(), request.date(),
            request.time());
        reservations.put(id, reservation);

        return ResponseEntity
            .created(URI.create("/reservations/" + id))
            .body(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.remove(id);
        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }

    private List<Reservation> getSortedReservations() {
        return reservations.values().stream()
            .sorted(Comparator.comparing(Reservation::getId))
            .toList();
    }
}
