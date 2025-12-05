package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Time> rowMapper = (resultSet, rowNum) ->
            new Time(
                    resultSet.getLong("id"),
                    resultSet.getString("time")
            );

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT id, time FROM time", rowMapper);
    }

    public long insert(String time) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", time);
        Number key = simpleInsert.executeAndReturnKey(params);
        return key.longValue();
    }

    public Time findById(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, time FROM time WHERE id = ?",
                rowMapper,
                id
        );
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }
}


