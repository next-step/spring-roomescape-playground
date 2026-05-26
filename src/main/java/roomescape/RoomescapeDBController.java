package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;
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
        String sqlQuery = "SELECT r.id, r.name, r.date, t.time from Reservations as r INNER JOIN Times as t on r.id = t.time_id";
        List<Reservation> reservations = jdbcTemplate.query(
                sqlQuery, (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("t.time").toLocalTime()
                    );
                    return reservation;
                });
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation request) {
        String selectQuery = "SELECT r.date, t.time from Reservations as r INNER JOIN Times as t on r.id = t.time_id";
        List<LocalDate> reservationDates = jdbcTemplate.query(
                selectQuery, (rs, rowNum) -> rs.getDate("date").toLocalDate()
        );
        List<LocalTime> reservationTimes = jdbcTemplate.query(
                selectQuery, (rs, rowNum) -> rs.getTime("time").toLocalTime()
        );
        checkDuplicateException(request.getDate(), request.getTime(), reservationDates, reservationTimes);

        String sqlQuery = "INSERT INTO Reservations(name, date) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, request.getName(), request.getDate());

        String latestIdQuery = "SELECT id FROM Reservations ORDER BY id DESC LIMIT 1";
        Integer latestId = jdbcTemplate.queryForObject(latestIdQuery, Integer.class);

        String insertTimeQuery = "INSERT INTO Times(time_id, time) VALUES (?, ?)";
        jdbcTemplate.update(insertTimeQuery, latestId, request.getTime());

        Reservation reservation = new Reservation(
                latestId,
                request.getName(),
                request.getDate(),
                request.getTime()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + reservation.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int id) {
        String deleteTimeQuery = "DELETE FROM Times WHERE time_id = ?";
        jdbcTemplate.update(deleteTimeQuery, id);

        String sqlQuery = "DELETE FROM Reservations WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sqlQuery, id);
        if (deletedRows == 0) {
            throw new ReservationException.NotFoundReservationException("해당 예약이 존재하지 않습니다.");
        }

    }

    private void checkDuplicateException(LocalDate requestDate, LocalTime requestTime,
                                         List<LocalDate> reservationDates, List<LocalTime> reservationTimes) {
        LocalDateTime requestDateTime = LocalDateTime.of(requestDate, requestTime);
        List<LocalDateTime> reservationDateTimes = IntStream.range(0, reservationDates.size())
                .mapToObj(i -> LocalDateTime.of(reservationDates.get(i), reservationTimes.get(i)))
                .toList();
        boolean isDuplicate = reservationDateTimes.stream()
                .anyMatch(reservationTime -> reservationTime.isEqual(requestDateTime));

        if (isDuplicate) {
            throw new ReservationException.DuplicateTimeException();
        }
    }
}
