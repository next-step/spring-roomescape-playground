package roomescape.time.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.exception.NotFoundReservationException;
import roomescape.time.Time;
import roomescape.time.TimeDao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TimeDaoImpl implements TimeDao {

    public static final RowMapper<Time> TIME_ROW_MAPPER = (rs, rowNum) -> Time.ofExist(
            rs.getLong("id"),
            rs.getTime("time").toLocalTime()
    );
    private final JdbcTemplate jdbcTemplate;

    public TimeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Time> findById(Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, TIME_ROW_MAPPER, id));
    }

    @Override
    public List<Time> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, TIME_ROW_MAPPER);
    }

    @Override
    public Time save(Time time) {
        String sql = "INSERT INTO reservation_time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, java.sql.Time.valueOf(time.getTime()));
            return ps;
        }, keyHolder);

        return Time.ofExist(
                Objects.requireNonNull(keyHolder.getKey()).longValue(),
                time.getTime()
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";

        if (findById(id).isEmpty()) {
            throw new IllegalArgumentException();
        }

        jdbcTemplate.update(sql, id);
    }
}
