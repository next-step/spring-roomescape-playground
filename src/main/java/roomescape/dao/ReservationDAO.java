package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public List<Reservation> findAllReservations() {
        final var query = "SELECT id, name, date, time FROM RESERVATION";

        List<Reservation> reservations = jdbcTemplate.query(query, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        ));

        return new ArrayList<>(reservations);
    }

    public Reservation  findReservationById(final Long id) {
        final var query = "SELECT * FROM RESERVATION WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")),
                    LocalTime.parse(resultSet.getString("time"))
            ), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("해당 Reservation을 찾을 수 없습니다. ID: " + id);
        }
    }
}
