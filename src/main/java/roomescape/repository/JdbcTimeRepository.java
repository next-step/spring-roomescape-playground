package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.TimeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<TimeEntity> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, this::mapRowToTime);
    }

    @Override
    public Optional<TimeEntity> findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        return jdbcTemplate.query(sql, this::mapRowToTime, id)
                .stream()
                .findFirst();
    }

    @Override
    public TimeEntity save(TimeEntity time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.time());

        long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new TimeEntity(id, time.time());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private TimeEntity mapRowToTime(ResultSet rs, int rowNum) throws SQLException {
        return new TimeEntity(
                rs.getLong("id"),
                rs.getString("time")
        );
    }
}
