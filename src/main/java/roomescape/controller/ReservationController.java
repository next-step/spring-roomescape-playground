package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.RequestReservation;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        List<Reservation> reservations = jdbcTemplate.query("SELECT * FROM reservation",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time")
                ));
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody RequestReservation requestReservation) {
        Long id = index.getAndIncrement();
        String name = requestReservation.name();
        String date = requestReservation.date();
        String time = requestReservation.time();

        Reservation newReservation = new Reservation(id, name, date, time);
        reservations.add(newReservation);
        return ResponseEntity.status(CREATED)
                .header(HttpHeaders.LOCATION, "/reservations/" + id)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{reservationId}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservations(@PathVariable Long reservationId) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(reservationId));
        if (!removed) {
            throw new IllegalArgumentException("Reservation not found");
        }
        return ResponseEntity.noContent().build();
    }
}
