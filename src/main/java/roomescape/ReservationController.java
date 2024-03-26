package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReservationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    public ReservationController(DataSource dataSource) {
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")));
    }

    @PostMapping("/reservations")
    public ResponseEntity<String> addReservation(@RequestBody Reservation reservation) {
        validateReservation(reservation);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time", reservation.getTime());

        Number newId = jdbcInsert.executeAndReturnKey(parameters);

        // 새로운 예약이 추가되면 바로 해당 예약을 반환합니다.
        Reservation addedReservation = jdbcTemplate.queryForObject(
                "SELECT id, name, date, time FROM reservation WHERE id = ?",
                new Object[]{newId},
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")));

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Location", "/reservations/" + newId.longValue())
                .body(addedReservation.toString());
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (rowsAffected == 0) {
            throw new NotFoundReservationException("Reservation not found with id: " + id);
        }

        // 삭제 후에는 바로 빈 목록을 반환합니다.
        return ResponseEntity.noContent().build();
    }

    private void validateReservation(Reservation reservation) {
        if (reservation == null ||
                reservation.getName() == null || reservation.getName().isEmpty() ||
                reservation.getDate() == null || reservation.getDate().isEmpty() ||
                reservation.getTime() == null || reservation.getTime().isEmpty()) {
            throw new NotFoundReservationException("Required fields are missing.");
        }
    }
}
