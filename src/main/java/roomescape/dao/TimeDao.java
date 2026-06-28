package roomescape.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time ORDER BY id";
        return jdbcTemplate.query(sql, timeRowMapper());
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        List<Time> times = jdbcTemplate.query(sql, timeRowMapper(), id);
        return times.stream().findFirst();
    }

    public boolean existsByTime(LocalTime time) {
        String sql = "SELECT COUNT(1) FROM time WHERE time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, time);
        return count != null && count > 0;
    }

    public Time save(Time reservationTime) {
        Number key = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "time", reservationTime.getTime()
        ));
        return new Time(key.longValue(), reservationTime.getTime());
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, id);
        return updatedRows > 0;
    }

    private RowMapper<Time> timeRowMapper() {
        return (resultSet, rowNum) -> new Time(
                resultSet.getLong("id"),
                resultSet.getObject("time", LocalTime.class)
        );
    }
}
