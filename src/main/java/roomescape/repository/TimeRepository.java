package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;


    public TimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT * FROM time", rowMapper());
    }

    public Optional<Time> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM time WHERE id = ?", rowMapper(), id).stream()
                .findFirst();
    }

    public boolean existsByTime(LocalTime time) {
        String sql = "SELECT COUNT(*) FROM time WHERE time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, time.toString());
        return count > 0;
    }

    public Time insert(Time time) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("time", time.getTime());
        Long key = jdbcInsert.executeAndReturnKey(source).longValue();
        return Time.withId(key, time);
    }

    public void delete(Time time) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", time.getId());
    }

    private RowMapper<Time> rowMapper() {
        return (resultSet, rowNum) -> Time.withId(
                resultSet.getLong("id"),
                Time.from(LocalTime.parse(resultSet.getString("time")))
        );
    }
}
