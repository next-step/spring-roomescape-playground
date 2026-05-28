package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.domain.Time;
import roomescape.domain.Times;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;
import roomescape.exception.ReservationConflictException;
import roomescape.validator.ReservationValidator;
import roomescape.validator.TimeValidator;

@Controller
public class RoomescapeController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationInsert;
    private final SimpleJdbcInsert timeInsert;
    private final ReservationValidator reservationValidator;
    private final TimeValidator timeValidator;

    public RoomescapeController(JdbcTemplate jdbcTemplate, ReservationValidator reservationValidator, TimeValidator timeValidator) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationValidator = reservationValidator;
        this.timeValidator = timeValidator;

        this.reservationInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        this.timeInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String showTimePage() {
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public List<TimeResponse> getTimes() {
        List<Time> timeList = jdbcTemplate.query(
                "SELECT id, time FROM time",
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getTime("time").toLocalTime()
                ));

        Times times = new Times(timeList);

        return times.getTimes().stream()
                .map(TimeResponse::from)
                .toList();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeRequest request) {
        Time tempTime = request.toEntity(null);
        timeValidator.validateDuplicate(tempTime);

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("time", java.sql.Time.valueOf(tempTime.getTime()));

        Long id = timeInsert.executeAndReturnKey(params).longValue();

        Time savedTime = request.toEntity(id);
        TimeResponse response = TimeResponse.from(savedTime);

        return ResponseEntity.created(URI.create("/times/" + id)).body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        int deleted = jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);

        if (deleted == 0) {
            throw new NotFoundTimeException();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> getReservations() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r " +
                "INNER JOIN time as t ON r.time_id = t.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Time time = new Time(
                    rs.getLong("time_id"),
                    rs.getTime("time_value").toLocalTime()
            );

            Reservation reservation = new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    time
            );

            return ReservationResponse.from(reservation);
        });
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Time time;
        try {
            time = jdbcTemplate.queryForObject(
                    "SELECT id, time FROM time WHERE id = ?",
                    (rs, rowNum) -> new Time(rs.getLong("id"), rs.getTime("time").toLocalTime()),
                    request.getTimeId()
            );
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new roomescape.exception.InvalidReservationException("존재하지 않는 예약 시간입니다.");
        }

        Reservation tempReservation = new Reservation(null, request.getName(), request.getDate(), time);
        reservationValidator.validateDuplicate(tempReservation);

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", tempReservation.getName())
                .addValue("date", java.sql.Date.valueOf(tempReservation.getDate()))
                .addValue("time_id", tempReservation.getTimeId());

        Long id = reservationInsert.executeAndReturnKey(params).longValue();

        Reservation savedReservation = new Reservation(id, tempReservation.getName(), tempReservation.getDate(), time);
        ReservationResponse response = ReservationResponse.from(savedReservation);

        return ResponseEntity.created(URI.create("/reservations/" + id)).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        int deleted = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);

        if (deleted == 0) {
            throw new NotFoundReservationException();
        }
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleException(NotFoundReservationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity<String> handleNotFoundTimeException(NotFoundTimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<String> handleBadRequest(InvalidReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<String> handleReservationConflictException(ReservationConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
