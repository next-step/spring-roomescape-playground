package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;
import roomescape.Exception.NotFoundReservationException;
import roomescape.Service.ReservationService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ReservationServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> getAllReservations() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time_id FROM reservation",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getLong("time_id")));
    }

    @Override
    public ResponseEntity<Reservation> addReservation(Reservation reservation) {
        validateReservation(reservation);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTimeId());

        Number newId = jdbcInsert.executeAndReturnKey(parameters);

        Reservation addedReservation = jdbcTemplate.queryForObject(
                "SELECT id, name, date, time_id FROM reservation WHERE id = ?",
                new Object[]{newId},
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getLong("time_id")));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + newId);

        return new ResponseEntity<>(addedReservation, headers, HttpStatus.CREATED);
    }

    @Override
    public void cancelReservation(Long id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (rowsAffected == 0) {
            throw new NotFoundReservationException("Reservation not found with id: " + id);
        }
    }

    private void validateReservation(Reservation reservation) {
        if (reservation == null ||
                reservation.getName() == null || reservation.getName().isEmpty() ||
                reservation.getDate() == null || reservation.getDate().isEmpty() ||
                reservation.getTimeId() <= 0) { // TimeId가 0 또는 음수인 경우를 검사
            throw new NotFoundReservationException("Required fields are missing.");
        }
    }
}