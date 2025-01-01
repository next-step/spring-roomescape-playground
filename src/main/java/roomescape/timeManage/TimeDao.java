package roomescape.timeManage;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TimeDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TimeDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return namedParameterJdbcTemplate.query(sql, timeRowMapper);
    }

    public Time findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, timeRowMapper);
    }

    public Time insert(String time) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("time")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time);
        Number generatedKey = simpleJdbcInsert.executeAndReturnKey(parameters);

        return findById(generatedKey.longValue());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> new Time(
            rs.getLong("id"),
            rs.getString("time")
    );
}
