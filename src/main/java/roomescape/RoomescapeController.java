package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;


import java.net.URI;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class RoomescapeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> findReservations() {
        List<Reservation> reservationList = jdbcTemplate.query(
                "select id, name, time, date from reservation",
                (resultSet, rowNum) -> {

                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("time"),
                            resultSet.getString("date")
                            );
                    return reservation;
                });

        return ResponseEntity.ok(reservationList);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        if(reservation.getName().isEmpty() || reservation.getDate().isEmpty() || reservation.getDate().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, time, date) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getTime());
            ps.setString(3, reservation.getDate());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        Reservation newReservation = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());

        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteReservation(@PathVariable() int id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) from reservation where id = ?", Integer.class, id);
        if (count == 0) return ResponseEntity.badRequest().build();

        jdbcTemplate.update(sql, id);

        return null;
    }
}