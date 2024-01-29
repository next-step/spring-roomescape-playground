package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getTime("time").toLocalTime()
        );
        return time;
    };

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAllTimes() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time findSpecificTime(Long timeId) {
        String sql = "SELECT id, time FROM time WHERE id=" + timeId;
        return jdbcTemplate.queryForObject(sql, timeRowMapper);
    }

    public Long insertNewTime(Time time) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingColumns("time")
                .usingGeneratedKeyColumns("id");

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(time);
        Number key = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);

        return key.longValue();
    }

    public Long updateTime(Time time, Long id) {
        String sql = "UPDATE time SET time=? WHERE id=?";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, time.getTime().toString());
            ps.setString(2, time.getId().toString());
            return ps;
        });

        return id;
    }

    public void deleteTime(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
