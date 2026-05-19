package roomescape;

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
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.ReservationConflictException;

@Controller
public class RoomescapeController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final ReservationValidator reservationValidator;

    public RoomescapeController(JdbcTemplate jdbcTemplate, ReservationValidator reservationValidator) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationValidator = reservationValidator;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> getReservations() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                (rs, rowNum) -> ReservationResponse.from(new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                )));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Reservation tempReservation = request.toEntity(null); //DB들어가기 전, 식별자 없는 객체 검증
        reservationValidator.validateDuplicate(tempReservation);

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", tempReservation.getName())
                .addValue("date", java.sql.Date.valueOf(tempReservation.getDate()))
                .addValue("time", java.sql.Time.valueOf(tempReservation.getTime()));

        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        Reservation savedReservation = request.toEntity(id);//검증을 모두 통과한 객체
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

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<String> handleBadRequest(InvalidReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<String> handleReservationConflictException(ReservationConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
