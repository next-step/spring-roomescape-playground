package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.Time;
import roomescape.repository.reservation.interfaces.TimeRepository;

@Repository
public class TimeDatabaseRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> {
        return new Time(rs.getLong("time_id"), rs.getTime("available_time").toLocalTime());
    };

    public TimeDatabaseRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source).withTableName("times").usingGeneratedKeyColumns("time_id");
    }

    public Long save(Time time) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(time);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT time_id, available_time FROM times WHERE time_id = ?";
        return jdbcTemplate.query(sql, timeRowMapper, id).stream().findFirst();
    }

    @Override
    public Optional<Time> findByTime(LocalTime time) {
        String sql = "SELECT time_id, available_time FROM times WHERE available_time = ?";
        return jdbcTemplate.query(sql, timeRowMapper, time).stream().findFirst();
    }

    public List<Time> findAll() {
        List<Time> times = jdbcTemplate.query("SELECT time_id, available_time FROM times", timeRowMapper);
        return List.copyOf(times);
    }

    public void delete(Long timeId) {
        String sql = "DELETE FROM times WHERE time_id = ?";
        jdbcTemplate.update(sql, timeId);
    }
}
