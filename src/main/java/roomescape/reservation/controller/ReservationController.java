package roomescape.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.mapper.ReservationRowMapper;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.exception.InvalidReservationException;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        String reservationName = reservation.getName();
        String reservationDate = reservation.getDate();
        String reservationTime = reservation.getTime();

        if(reservationName.isEmpty() || reservationDate.isEmpty() || reservationTime.isEmpty()){
            throw new InvalidReservationException("Invalid reservation data, Field Empty");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservationName);
            ps.setString(2, reservationDate);
            ps.setString(3, reservationTime);
            return ps;
        }, keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        Reservation newReservation = Reservation.toEntity(reservation, id);

        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){

        String selectQuery = "SELECT COUNT(*) FROM reservation WHERE id = ?";
        int count = jdbcTemplate.queryForObject(selectQuery, Integer.class, id);

        if (count == 0) {
            throw new InvalidReservationException("Reservation not found with id: " + id);
        }

        String deleteQuery = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(deleteQuery, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
