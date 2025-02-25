package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.dto.time.response.TimeResponse;
import roomescape.mapper.TimeRowMapper;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final TimeRowMapper timeRowMapper;

    public TimeDAO(JdbcTemplate jdbcTemplate, TimeRowMapper timeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
        this.timeRowMapper = timeRowMapper;
    }

    public Time createTime(Time time) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(time);
        long key = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return new Time(key, time.getTime());
    }

    public List<Time> findTimes() {
        String sql = "select id, time from time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }
}
