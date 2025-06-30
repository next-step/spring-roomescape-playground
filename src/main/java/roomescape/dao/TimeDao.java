package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
            .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT id, time FROM time",
            (rs, rowNum) ->
                new Time(
                    rs.getLong("id"),
                    LocalTime.parse(rs.getString("time"))
                )
        );
    }

    public Time findById(Long id) {
        String sql = "SELECT id, time FROM time where id = ?";
        List<Time> results = jdbcTemplate.query(sql,
            (rs, rowNum) -> new Time(
                rs.getLong("id"),
                LocalTime.parse(rs.getString("time"))
            ), id);
        if (results.isEmpty()) {
            throw new TimeNotFoundException(id);
        }
        return results.get(0);
    }

    public boolean existsByTime(LocalTime time) {
        String sql = "SELECT COUNT(*) FROM time WHERE time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, time.toString());
        return count > 0;
    }

    public Time save(Time time) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(time);
        Number key = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return new Time(key.longValue(), time.getTime());
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) {
            throw new TimeNotFoundException(id);
        }
    }
}
