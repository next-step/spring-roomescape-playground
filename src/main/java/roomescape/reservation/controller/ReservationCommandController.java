package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.NotFoundReservationException;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.reservation.dto.ReservationCreateResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationCommandController {

    private final JdbcTemplate jdbcTemplate;

    private final AtomicLong index = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationCommandController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservationList() {
        String sql = "SELECT * FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
        ));
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
