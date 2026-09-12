package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        return jdbcTemplate.query(
                "SELECT id, time FROM time ORDER BY time",
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getObject("time", LocalTime.class)
                )
        );
    }

    public Optional<Time> findById(Long id) {
        return jdbcTemplate.query(
                        "SELECT id, time FROM time WHERE id = ?",
                        (rs, rowNum) -> new Time(
                                rs.getLong("id"),
                                rs.getObject("time", LocalTime.class)
                        ),
                        id
                )
                .stream()
                .findFirst();
    }

    public Time save(Time time) {
        Map<String, Object> parameters = Map.of(
                "time", time.getTime()
        );

        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return new Time(
                id,
                time.getTime()
        );
    }

    public boolean deleteById(Long id) {
        int deleteCount = jdbcTemplate.update(
                "DELETE FROM time WHERE id = ?",
                id
        );

        return deleteCount > 0;
    }
}
