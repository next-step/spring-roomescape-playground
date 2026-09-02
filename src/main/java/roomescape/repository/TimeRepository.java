package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Time> rowMapper = (resultSet, rowNum) -> new Time(
            resultSet.getLong("id"),
            LocalTime.parse(resultSet.getString("time"))
    );

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT id, time FROM time", rowMapper);
    }

    public Optional<Time> findById(long id) {
        List<Time> result = jdbcTemplate.query(
                "SELECT id, time FROM time WHERE id = ?",
                rowMapper,
                id
        );
        return result.stream().findFirst();
    }

    public Time save(Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO time(time) VALUES (?)",
                    new String[]{"id"}
            );
            preparedStatement.setString(1, time.getTime().toString());
            return preparedStatement;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return time.withId(id);
    }

    public boolean existsByTime(LocalTime time) {
        String sql = "SELECT COUNT(*) FROM time WHERE time = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, time.toString());

        return count != null && count > 0;
    }

    public boolean deleteById(long id) {
        int deletedCount = jdbcTemplate.update(
                "DELETE FROM time WHERE id = ?",
                id
        );
        return deletedCount > 0;
    }
}
