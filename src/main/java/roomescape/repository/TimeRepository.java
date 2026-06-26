package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

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

    public Optional<Time> findById(long id) {
        String sql = """
                SELECT id,time
                FROM time
                where id = ?
                """;
        List<Time> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getString("time")
                ),
                id
        );
        return result.stream().findFirst();
    }

    public long insert(Time time) {
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
