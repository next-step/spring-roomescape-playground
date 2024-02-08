package roomescape.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rownumber)
            -> {
        Time time = new Time(
                resultSet.getInt("id"),
                resultSet.getTime("time").toLocalTime()
        );
        return time;
    };
    public List<Time> getAllTime() {
        String sql = "SELECT id,time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }
    public int save(Time time) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingColumns("time")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(time);
        Number key = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        return key.intValue();
    }
    public void deleteTimeById(int id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}