package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.RequestReservation;
import roomescape.model.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
public class ReservationController {
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        List<Reservation> reservations = simpleJdbcInsert.getJdbcTemplate()
                .query("SELECT * FROM reservation",
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
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", requestReservation.name());
        parameters.put("date", requestReservation.date());
        parameters.put("time", requestReservation.time());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        Reservation newReservation = simpleJdbcInsert.getJdbcTemplate()
                .queryForObject("SELECT * FROM reservation WHERE id = ?",
                        (rs, rowNum) -> new Reservation(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("date"),
                                rs.getString("time")
                        ),
                        newId.longValue()
                );

        return ResponseEntity.status(CREATED)
                .header(HttpHeaders.LOCATION, "/reservations/" + newId)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{reservationId}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservations(@PathVariable Long reservationId) {
        int rowsAffected = simpleJdbcInsert.getJdbcTemplate().update("DELETE FROM reservation WHERE id = ?", reservationId);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Reservation not found");
        }
        return ResponseEntity.noContent().build();
    }
}
