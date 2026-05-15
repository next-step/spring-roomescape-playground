package roomescape;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class RoomescapeDBController {

    private final JdbcTemplate jdbcTemplate;

    public RoomescapeDBController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> showAllReservations() {
        String sqlQuery = "SELECT * from Reservations";
        List<Reservation> reservations = jdbcTemplate.query(
                sqlQuery, (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getTimestamp("datetime").toLocalDateTime()
                    );
                    return reservation;
                });
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation request) {
        String selectQuery = "SELECT datetime from Reservations";
        List<LocalDateTime> reservationTime = jdbcTemplate.query(
                selectQuery, (resultSet, rowNum) -> resultSet.getObject("datetime", LocalDateTime.class)
        );
        checkDuplicateException(request.getDateTime(), reservationTime);

        String sqlQuery = "INSERT INTO Reservations(name, datetime) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, request.getName(), request.getDateTime());

        sqlQuery = "SELECT * FROM Reservations ORDER BY id DESC LIMIT 1";
        Reservation reservation = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) ->
                new Reservation(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getTimestamp("datetime").toLocalDateTime()
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + reservation.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int id) {
        String sqlQuery = "DELETE FROM Reservations WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sqlQuery, id);
        if (deletedRows == 0) {
            throw new ReservationException.NotFoundReservationException("해당 예약이 존재하지 않습니다.");
        }

    }

    private void checkDuplicateException(LocalDateTime requestTime, List<LocalDateTime> reservationTimes) {
        boolean isDuplicate = reservationTimes.stream()
                .anyMatch(reservationTime -> reservationTime.isEqual(requestTime));

        if (isDuplicate) {
            throw new ReservationException.DuplicateTimeException();
        }
    }
}
