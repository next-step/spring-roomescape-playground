package roomescape.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Time;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRawMapper = (resultSet, rowNum) ->
        new Time(resultSet.getLong("id"),
            resultSet.getTime("time").toLocalTime());

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> listAllTimes() {
        String sql = "SELECT id, time FROM time";

        return jdbcTemplate.query(sql, timeRawMapper);
    }

    public Long createTime(Time time) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("time")
            .usingColumns("time")
            .usingGeneratedKeyColumns("id");

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(time);
        Number key = simpleJdbcInsert.executeAndReturnKey(parameterSource);

        return key.longValue();
    }

    public int deleteTime(Long id) {
        String sql = "DELETE FROM time where id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
