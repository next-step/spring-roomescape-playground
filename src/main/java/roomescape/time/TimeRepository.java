package roomescape.time;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.dto.TimeCreateRequest;
import roomescape.time.dto.TimeResponse;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<TimeResponse> findAll() {
        String sql = """
                SELECT id, time
                  FROM time
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new TimeResponse(
                        rs.getLong("id"),
                        rs.getString("time")
                )
        );
    }

    public TimeResponse save(TimeCreateRequest timeRequest) {
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

            ps.setString(1, timeRequest.time());

            return ps;
        }, keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new TimeResponse(id, timeRequest.time());
    }

    public int deleteById(Long id) {
        String sql = """
                DELETE FROM TIME
                 WHERE id = ?
                """;

        return jdbcTemplate.update(sql, id);
    }
}
