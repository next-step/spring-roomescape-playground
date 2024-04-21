package roomescape.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.InvalidReservationException;
import roomescape.mapper.ReservationTimeRowMapper;
import roomescape.time.domain.ReservationTime;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ReservationTimeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ReservationTimeRowMapper reservationTimeRowMapper = new ReservationTimeRowMapper();

    @GetMapping("/time")
    public String reservationTime(){
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTime reservationTime){
        String time = reservationTime.getReservationTime();

        if (Optional.ofNullable(time).orElse("").isEmpty()) {
            throw new InvalidReservationException("Invalid Reservation Time data, Time Field Empty");
        }

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time);

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        Long id = newId.longValue();

        ReservationTime newReservationTime = ReservationTime.toEntity(reservationTime, id);

        return ResponseEntity.created(URI.create("/times/" + id)).body(newReservationTime);
    }

    @GetMapping("/times")
    @ResponseBody
    public List<ReservationTime> getReservationTimes(){
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id){
        String selectQuery = "SELECT id FROM time WHERE id = ?";
        List<Long> timeIds = jdbcTemplate.queryForList(selectQuery, Long.class, id);

        if (!timeIds.isEmpty()) {
            String deleteQuery = "DELETE FROM time WHERE id = ?";
            jdbcTemplate.update(deleteQuery, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new InvalidReservationException("Reservation Time not found with id: " + id);
        }
    }

}
