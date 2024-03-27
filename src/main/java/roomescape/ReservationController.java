package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation",
                (rs, rowNum) -> new Reservation (
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time")
                ));
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        if (reservation.getName() == null || reservation.getName().isEmpty() ||
                reservation.getDate() == null || reservation.getDate().isEmpty() ||
                reservation.getTime() == null || reservation.getTime().isEmpty()) {
            throw new NotFoundReservationException("Required fields are missing.");
        }

        long newId = index.incrementAndGet();
        reservation.setId(newId);
        reservations.add(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + newId)
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId() == id);
        if (!removed) {
            throw new NotFoundReservationException("Reservation not found.");
        }
        return ResponseEntity.noContent().build();
    }
}