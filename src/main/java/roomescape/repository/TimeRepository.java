package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.util.List;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert timeInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        return jdbcTemplate.query(
                "SELECT id, time FROM time",
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getTime("time").toLocalTime()
                ));
    }

    public Long save(Time time) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("time", java.sql.Time.valueOf(time.getTime()));
        return timeInsert.executeAndReturnKey(params).longValue();
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    public Integer countByTime(java.sql.Time time) {
        String sql = "SELECT count(1) FROM time WHERE time = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, time);
    }

    public Time findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT id, time FROM time WHERE id = ?",
                    (rs, rowNum) -> new Time(rs.getLong("id"), rs.getTime("time").toLocalTime()),
                    id
            );
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
}
