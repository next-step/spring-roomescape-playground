package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.LocalTime;

@Repository
public class TimeRepository {

    private static final RowMapper<ReservationTime> ROW_MAPPER =
            (rs, rowNum) -> new ReservationTime(
                    rs.getLong("id"),
                    rs.getObject("time", LocalTime.class)
            );

    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, time FROM reservation_time ORDER BY id";

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT id, time FROM reservation_time WHERE id = ?";

        return jdbcTemplate.query(sql, ROW_MAPPER, id)
                .stream()
                .findFirst();
    }

    public ReservationTime save(LocalTime time) {
        String sql = "INSERT INTO reservation_time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setObject(1, time);
            return statement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new ReservationTime(id, time);
    }

    public int delete(Long id) {
        try {
            return jdbcTemplate.update(
                    "DELETE FROM reservation_time WHERE id = ?",
                    id
            );
        } catch (DataIntegrityViolationException exception) {
            throw new ReservationTimeInUseException(exception);
        }
    }
}
