package roomescape.time;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.Time;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Time> findAll() {
        String sql = """
                SELECT id, time
                  FROM time
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Time(
                        rs.getLong("id"),
                        rs.getString("time")
                )
        );
    }

    public Time findById(Long id) {
        String sql = """
                SELECT id, time
                  FROM time
                 WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Time(
                        rs.getLong("id"),
                        rs.getString("time")
                ), id);
    }

    public Time save(Time time) {
        String sql = """
                INSERT INTO time(time)
                VALUES (?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, time.getFormattedTime());

            return ps;
        }, keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new Time(id, time.getFormattedTime());
    }

    public int deleteById(Long id) {
        String sql = """
                DELETE FROM TIME
                 WHERE id = ?
                """;

        return jdbcTemplate.update(sql, id);
    }
}
