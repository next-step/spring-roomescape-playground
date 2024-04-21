package roomescape.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.mapper.ReservationRowMapper;
import roomescape.reservation.domain.Reservation;
import roomescape.exception.InvalidReservationException;
import roomescape.time.domain.Time;

import java.net.URI;
import java.util.*;

@Controller
public class ReservationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ReservationRowMapper reservationRowMapper = new ReservationRowMapper();

    @GetMapping("/reservation")
    public String reservation(){
        return "new-reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value FROM reservation AS r INNER JOIN time AS t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        String reservationName = reservation.getName();
        String reservationDate = reservation.getDate();
        Time reservationTime= reservation.getTime();

        /* 유효성 검사 */
        if (Optional.ofNullable(reservationName).orElse("").isEmpty() ||
                Optional.ofNullable(reservationDate).orElse("").isEmpty() ||
                reservation.getTime() == null) {
            throw new InvalidReservationException("Invalid reservation data, Field Empty");
        }

        /*  Time 객체의 id를 사용하여 실제 Time 객체를 조회 */
        String selectQuery = "SELECT id, time FROM time WHERE id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(selectQuery, reservationTime.getId());

        /*  id에 해당하는 Time 객체가 여러 개라면 Exception 발생 */
        if (!rowSet.next()) {
            throw new InvalidReservationException("Invalid Time ID: " + reservationTime.getId());
        }

        Time existingTime = new Time(rowSet.getLong("id"), rowSet.getString("time"));

        /*  Time 객체의 id를 Reservation 테이블에 Insert */
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservationName);
        parameters.put("date", reservationDate);
        parameters.put("time_id", existingTime.getId());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        Long id = newId.longValue();

        /*  새로운 Reservation 객체를 반환 */
        Reservation newReservation = Reservation.toEntity(id, reservationName, reservationDate, existingTime);

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
