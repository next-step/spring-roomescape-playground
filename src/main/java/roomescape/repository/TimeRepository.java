package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;

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

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) ->
                        new Time(
                                resultSet.getLong("id"),
                                resultSet.getTime("time").toLocalTime()
                        )
        );
    }

    public Time save(Time reservationTime) {

        String sql = """
                INSERT INTO Time(time)
                VALUES (?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setTime(1, java.sql.Time.valueOf(reservationTime.time()));

            return statement;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException("생성된 ID를 가져올 수 없습니다.");
        }

        Long id = key.longValue();

        return new Time(
                id,
                reservationTime.time()
        );
    }

    public int delete(Long id) {

        String sql = """
                DELETE FROM time
                WHERE id = ?
                """;

        return jdbcTemplate.update(sql, id);
    }
}
