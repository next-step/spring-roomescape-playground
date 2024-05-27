package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;
import roomescape.model.Time;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "new-reservation"; // new-reservation.html을 사용하도록 수정
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r INNER JOIN time as t ON r.time_id = t.id";
        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Reservation reservation = new Reservation();
            reservation.setId(rs.getLong("reservation_id"));
            reservation.setName(rs.getString("name"));
            reservation.setDate(rs.getString("date"));

            Time time = new Time();
            time.setId(rs.getLong("time_id"));
            time.setTime(rs.getString("time_value"));
            reservation.setTime(time);

            return reservation;
        });
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime().getId());

        sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r INNER JOIN time as t ON r.time_id = t.id " +
                "WHERE r.id = (SELECT MAX(id) FROM reservation)";
        Reservation newReservation = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Reservation res = new Reservation();
            res.setId(rs.getLong("reservation_id"));
            res.setName(rs.getString("name"));
            res.setDate(rs.getString("date"));

            Time time = new Time();
            time.setId(rs.getLong("time_id"));
            time.setTime(rs.getString("time_value"));
            res.setTime(time);

            return res;
        });

        URI location = URI.create("/reservations/" + newReservation.getId());
        return ResponseEntity.created(location).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
