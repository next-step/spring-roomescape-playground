package roomescape.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.RequestTime;
import roomescape.domain.Time;

@Repository
public class TimeDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Time> rowMapper = (resultSet, rowNum) -> new Time(
            resultSet.getLong("id"),
            resultSet.getString("time")
    );

    public TimeDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Time save(final RequestTime requestTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", requestTime.time());

        Number newId = jdbcInsert.executeAndReturnKey(parameters);

        return new Time(newId.longValue(), requestTime.time());
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Time> findById(final long id) {
        String sql = "SELECT id, time FROM time WHERE id = :id";
        Map<String, Object> params = Map.of("id", id);
        List<Time> results = jdbcTemplate.query(sql, params, rowMapper);
        return results.stream().findFirst();
    }

    public void deleteById(final long id) {
        String sql = "DELETE FROM time WHERE id = :id";
        Map<String, Object> params = Map.of("id", id);
        jdbcTemplate.update(sql, params);
    }
}
