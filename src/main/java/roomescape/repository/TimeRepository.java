package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;

import java.time.LocalTime;
import java.util.List;

@Repository
public class TimeRepository {
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = Time.restore(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("time"))
        );
        return time;
    };

    private SimpleJdbcInsert insertTime;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertTime = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Time save(Time time){
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(time);
        Long newId = insertTime.executeAndReturnKey(parameterSource).longValue();

        return time.withId(newId);
    }

    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    public List<Time> findAllTimes() {
        List<Time> times = jdbcTemplate.query(
                "SELECT id, time from time", timeRowMapper);
        return times;
    }

    public Time findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        List<Time> times = jdbcTemplate.query(sql, timeRowMapper, id);
        if (times.isEmpty()) {
            return null;
        }
        return times.get(0);
    }


}
