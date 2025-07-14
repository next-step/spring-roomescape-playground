package roomescape.time.dao;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.time.model.Time;

@Component
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        return jdbcTemplate.query(
            "select id, time from time",
            (resultSet, rowNum) ->
                new Time(
                    resultSet.getLong("id"),
                    resultSet.getTime("time").toLocalTime()
                )
        );
    }

    public Optional<Time> findById(Long id) {
        Time time = jdbcTemplate.queryForObject(
            "select id, time from time where id = ?",
            (resultSet, rowNum) -> new Time(
                resultSet.getLong("id"),
                resultSet.getTime("time").toLocalTime()
            ), id
        );

        return Optional.ofNullable(time);
    }

    public Time insert(LocalTime time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.toString());
        Long id = (Long) insert.executeAndReturnKey(parameters);
        return new Time(id, time);
    }

    public boolean delete(Long id) {
        return jdbcTemplate.update("DELETE FROM time WHERE id = ?", id) > 0;
    }
}
