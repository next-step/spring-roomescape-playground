package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.model.Reservation;

@Controller
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    // 조회
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        String sql = "SELECT * FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reservation.class));
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // 추가
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        reservation.setId(nextId++);
        reservations.add(reservation);

        URI location = URI.create("/reservations/" + reservation.getId());
        return ResponseEntity.created(location).body(reservation);
    }

    // 취소
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                reservations.remove(reservation);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
