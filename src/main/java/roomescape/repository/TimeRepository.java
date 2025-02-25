package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeRepository {

    private static final RowMapper<Time> TIME_ROW_MAPPER = (rs, rowNum) ->
            new Time(
                    rs.getLong("id"),
                    rs.getString("time")
            );

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TIME")
                .usingGeneratedKeyColumns("id");
    }

    public Time save(final Time time) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(time);
        long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Time(
                id,
                time.getTime()
        );
    }
}
