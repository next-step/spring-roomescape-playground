package roomescape.repository_layer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;

    public Long insert(Time time) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = Map.of("time", time.getTime());
        Number id = insertActor.executeAndReturnKey(parameters);
        return id.longValue();
    }

    public List<Time> findAll() {
        String sql = "select id,time from time";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> Time.of(
                        resultSet.getLong("id"),
                        resultSet.getString("time")));
    }

    public Time findById(Long id) {
        String sql = "select id, time from time where id = ?";
        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) -> Time.of(
                        resultSet.getLong("id"),
                        resultSet.getString("time")
                ), id
        );
    }

    public int delete(Long id) {
        return jdbcTemplate.update("delete from time where id = ?", id);
    }

}
