package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAll() {

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

    public long insert(TimeRequest time) {
        String sql = """
                INSERT INTO time (time)
                VALUES (?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, time.time());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public boolean delete(Long id) {
        String sql = """
                DELETE FROM time WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        return result > 0;
    }
}
