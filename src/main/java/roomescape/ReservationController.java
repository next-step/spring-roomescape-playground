package roomescape;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundException;

@Controller
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<Void> handleInvalidReservationException() {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/reservation")
    public String adminPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> read() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> Reservation.create(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
            )
        );
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(
        @RequestBody ReservationRequest reservationRequest) {
        reservationRequest.validate();
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql,
                new String[]{"id"}
            );

            ps.setString(1, reservationRequest.name());
            ps.setString(2, reservationRequest.date());
            ps.setString(3, reservationRequest.time());

            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        Reservation reservation = Reservation.create(
            id,
            reservationRequest.name(),
            reservationRequest.date(),
            reservationRequest.time()
        );

        return ResponseEntity
            .created(URI.create("/reservations/" + id))
            .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        int deletedCount = jdbcTemplate.update(
            "DELETE FROM reservation WHERE id = ?",
            id
        );

        if (deletedCount == 0) {
            throw new NotFoundException("Reservation not found: id=" + id);
        }

        return ResponseEntity.noContent().build();
    }
}
