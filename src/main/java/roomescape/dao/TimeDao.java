package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.domain.Time;

import java.util.List;

@Component
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Time add(Time time) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(time);
        Long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return time.with(id);
    }

    public List<Time> getAll() {
        return jdbcTemplate.queryForStream(
                "SELECT id, time FROM time",
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getString("time")
                )
        ).toList();
    }

    public Time getById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, time FROM time where id = ?",
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getString("time")
                ),
                id
        );
    }

    public void delete(Long id) {
        int affectedCount = jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
        if (affectedCount == 0) {
            throw new IllegalArgumentException("id가 " + id + "인 시간을 찾을 수 없습니다");
        }
    }
}
