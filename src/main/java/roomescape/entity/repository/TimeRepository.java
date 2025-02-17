package roomescape.entity.repository;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Dto.TimeInDto;
import roomescape.entity.value.Time;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Time(
                rs.getLong("id"),
                rs.getString("time")));
    }

    public Time save(TimeInDto timeInDto) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("time", timeInDto.getTime());
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Time(id, timeInDto.getTime());
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT * FROM time WHERE id = ?";
        final List<Time> times = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) ->
            new Time(
                rs.getLong("id"),
                rs.getString("time")));
        return times.stream().findFirst();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
