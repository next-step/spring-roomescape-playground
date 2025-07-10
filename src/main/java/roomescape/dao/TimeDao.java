package roomescape.dao;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.TimeNotFoundException;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("time")
            .usingGeneratedKeyColumns("id")
            .usingColumns("time", "is_deleted");
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT id, time, is_deleted FROM time WHERE is_deleted = false",
            (rs, rowNum) ->
                new Time(
                    rs.getLong("id"),
                    LocalTime.parse(rs.getString("time")),
                    rs.getBoolean("is_deleted")
                )
        );
    }

    public Time findById(Long id) {
        String sql = "SELECT id, time, is_deleted FROM time where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Time(
                    rs.getLong("id"),
                    LocalTime.parse(rs.getString("time")),
                    rs.getBoolean("is_deleted")
                ), id);
        } catch (EmptyResultDataAccessException e) {
            throw new TimeNotFoundException(id);
        }
    }

    public Optional<Time> findDeletedByTime(LocalTime time) {
        String sql = "SELECT id, time, is_deleted FROM time WHERE time = ? AND is_deleted = true";
        try {
            Time result = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Time(
                    rs.getLong("id"),
                    LocalTime.parse(rs.getString("time")),
                    rs.getBoolean("is_deleted")
                ), time.toString());
            return Optional.of(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void revive(Long id) {
        String sql = "UPDATE time SET is_deleted = false WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Time save(Time time) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", time.getTime().toString());
        params.put("is_deleted", time.isDeleted());
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new Time(key.longValue(), time.getTime());
    }

    public void deleteById(long id) {
        String sql = "UPDATE time SET is_deleted = true WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) {
            throw new TimeNotFoundException(id);
        }
    }
}
