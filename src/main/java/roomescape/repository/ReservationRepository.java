package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = ReservationTime.createFromPersistedData(
                resultSet.getLong("time_id"),
                resultSet.getObject("time_value", LocalTime.class)
        );

        return Reservation.createFromPersistedData(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getObject("date", LocalDate.class),
                reservationTime
        );
    };

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.time AS time_value
                FROM reservation AS r
                INNER JOIN time AS t
                    ON r.time_id = t.id
                """;
        return jdbcTemplate.query(
                sql,
                reservationRowMapper);
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setObject(2, reservation.getDate());
            preparedStatement.setObject(3, reservation.getTime().getId());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return Reservation.createFromPersistedData(id, reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public boolean existsByTimeId(Long timeId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation WHERE time_id = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, timeId);
        return exists;
    }
}
