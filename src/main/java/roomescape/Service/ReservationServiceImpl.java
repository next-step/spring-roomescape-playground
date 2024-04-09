package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import roomescape.Domain.Reservation;
import roomescape.Exception.NotFoundReservationException;

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
                "SELECT id, name, date, time FROM reservation",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")));
    }

    @Override
    public ResponseEntity<Reservation> addReservation(Reservation reservation) {
        validateReservation(reservation);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time", reservation.getTime());

        Number newId = jdbcInsert.executeAndReturnKey(parameters);

        Reservation addedReservation = jdbcTemplate.queryForObject(
                "SELECT id, name, date, time FROM reservation WHERE id = ?",
                new Object[]{newId},
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")));

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
                reservation.getTime() == null || reservation.getTime().isEmpty()) {
            throw new NotFoundReservationException("Required fields are missing.");
        }
    }


}