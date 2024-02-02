package roomescape.infrastructure.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.domain.repository.TimeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcTimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("time")
                                                                  .usingGeneratedKeyColumns("id");
    }

    @Override
    public Time save(final Time time) {
        final Map<String, Object> params = new HashMap<>();
        params.put("time", time.getTime());

        final long insertedId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new Time(insertedId, time.getTime());
    }

    @Override
    public List<Time> findAll() {
        final String sql = "SELECT id, time FROM time";

        return jdbcTemplate.query(sql, getTimeRowMapper());
    }

    private static RowMapper<Time> getTimeRowMapper() {
        return (rs, rowNum) -> new Time(rs.getLong("id"), rs.getTime("time").toLocalTime());
    }
}
