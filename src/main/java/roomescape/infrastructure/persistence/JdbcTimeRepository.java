package roomescape.infrastructure.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.domain.repository.TimeRepository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTimeRepository implements TimeRepository {

    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcTimeRepository(final JdbcTemplate jdbcTemplate) {
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
}
