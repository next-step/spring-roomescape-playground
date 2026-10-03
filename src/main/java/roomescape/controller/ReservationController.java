package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.exception.NotFoundException;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> found = jdbcTemplate.query("select id, name, date, time from reservation;", (rs, idx) ->
                new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time"))
                ));
        return ResponseEntity.ok(found.stream().map(ReservationResponse::new).toList());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservation) {
        Reservation vali = new Reservation(null, reservation.getName(), reservation.getDate(), reservation.getTime());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement ps = c.prepareStatement("insert into reservation(name,date,time) values(?,?,?)", new String[]{"id"});
            ps.setString(1, vali.getName());
            ps.setString(2, vali.getDate().toString());
            ps.setString(3, vali.getTime().toString());
            return ps;
        }, keyHolder);
        Long newId = keyHolder.getKey().longValue();
        Reservation newReservation = new Reservation(
                newId,
                vali.getName(),
                vali.getDate(),
                vali.getTime());

        ReservationResponse dto = new ReservationResponse(newReservation);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/reservations/" + dto.getId()).body(dto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        int deleted = jdbcTemplate.update("delete from reservation where id = ?", id);
        if (deleted == 0) {
            throw new NotFoundException("예약을 찾을 수 없습니다. id =" + id);
        }
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> handNotFound(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handIllgealArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
