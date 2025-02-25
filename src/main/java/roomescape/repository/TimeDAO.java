package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
    }

    public Time createTime(Time time) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(time);
        long key = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return new Time(key, time.getTime());
    }
}
