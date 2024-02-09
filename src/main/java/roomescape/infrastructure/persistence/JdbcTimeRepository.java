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
import java.util.Optional;

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

    @Override
    public Optional<Time> findById(final Long timeId) {
        final String sql = "SELECT id, time FROM time WHERE id = ?";

        return jdbcTemplate.query(sql, getTimeRowMapper(), timeId)
                           .stream()
                           .findAny();
    }

    @Override
    public boolean existsById(final Long id) {
        final String sql = "SELECT COUNT(*) FROM time WHERE id = ?";
        final Long count = jdbcTemplate.queryForObject(sql, Long.class, id);

        return count != null && count > 0;
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static RowMapper<Time> getTimeRowMapper() {
        return (rs, rowNum) -> new Time(rs.getLong("id"), rs.getTime("time").toLocalTime());
    }
}
