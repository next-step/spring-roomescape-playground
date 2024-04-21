package roomescape.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.mapper.ReservationRowMapper;
import roomescape.reservation.domain.Reservation;
import roomescape.exception.InvalidReservationException;

import java.net.URI;
import java.util.*;

@Controller
public class ReservationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ReservationRowMapper reservationRowMapper = new ReservationRowMapper();

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        String sql = "SELECT id, name, date, time FROM reservation"; // 컬럼을 명시적으로 가져오도록 수정하였습니다.
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        String reservationName = reservation.getName();
        String reservationDate = reservation.getDate();
        String reservationTime = reservation.getTime();

        if (Optional.ofNullable(reservationName).orElse("").isEmpty() ||
                Optional.ofNullable(reservationDate).orElse("").isEmpty() ||
                Optional.ofNullable(reservationTime).orElse("").isEmpty()) {
            throw new InvalidReservationException("Invalid reservation data, Field Empty");
        }

        // KeyHolder -> SimpleJdbcInsert를 이용한 방식으로 변경
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservationName);
        parameters.put("date", reservationDate);
        parameters.put("time", reservationTime);

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        Long id = newId.longValue();

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
