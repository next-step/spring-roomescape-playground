package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("time_id"),
                resultSet.getString("time_value")
        );

        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                time
        );
        return reservation;
    };

    public List<Reservation> findAllReservations() {
        String sql = """
        SELECT
            r.id as reservation_id,
            r.name,
            r.date,
            t.id as time_id,
            t.time as time_value
        FROM reservation as r
        INNER JOIN time as t ON r.time_id = t.id
        """;
        List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper);

        return reservations;
    }

    public int deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowNum = jdbcTemplate.update(sql, Long.valueOf(id));
        return rowNum;
    }

    public Long insert(Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTime().getId());

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

}
