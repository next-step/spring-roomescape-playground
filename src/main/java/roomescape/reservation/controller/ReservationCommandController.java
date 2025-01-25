package roomescape.reservation.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.NotFoundReservationException;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.reservation.dto.ReservationCreateResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationCommandController {

    private final AtomicLong index = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();

    @PostConstruct
    void init() {
        reservations.add(new Reservation(index.incrementAndGet(), "브라운", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(index.incrementAndGet(), "SEOKJU", LocalDate.now(), LocalTime.now()));
        reservations.add(new Reservation(index.incrementAndGet(), "HONG", LocalDate.now(), LocalTime.now()));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservationList() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateResponse> createReservation(@RequestBody ReservationCreateRequest request) throws URISyntaxException {
        Reservation reservation = request.toEntity(index.incrementAndGet());
        reservations.add(reservation);
        Long id = reservation.getId();
        URI uri = new URI("/reservations/" + id.toString());
        return ResponseEntity
                .created(uri)
                .body(new ReservationCreateResponse(id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.isEqualId(id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
