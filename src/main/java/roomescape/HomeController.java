package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class HomeController {
    private JdbcTemplate jdbcTemplate;

    public HomeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        if (reservation.getName().isBlank() || reservation.getDate().isBlank() || reservation.getTime().isBlank()) {
            throw new IllegalArgumentException("Invalid reservation");
        }

        Long id = insertWithKeyHolder(reservation);

        Reservation newReservation = Reservation.toEntity(reservation, id);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int deleteNumber = deleteReservation(id);

        if(deleteNumber == 0) {
            throw new NotFoundReservationException("Reservation not found: id=" + id);
        }

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        System.out.println("IllegalArgumentException occurred: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper);

        return reservations;
    }

    public void insert(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public int deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowNum = jdbcTemplate.update(sql, Long.valueOf(id));
        return rowNum;
    }

    public Long insertWithKeyHolder(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1,reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
