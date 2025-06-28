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
