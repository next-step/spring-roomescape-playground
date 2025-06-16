package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;
import roomescape.repository.TimeRepository;

@Repository
public class TimeDAO implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingColumns("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
        return time;
    };

    @Override
    public Time findById(Long id) {
        String sql = "SELECT * FROM time WHERE id = ? ";

        return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
    }

    @Override
    public List<Time> findAll() {
        String sql = "SELECT * FROM time";

        return jdbcTemplate.query(sql, timeRowMapper);
    }

    private SqlParameterSource createSqlParameterSource(Time time) {
        return new MapSqlParameterSource()
                .addValue("time", time.getTime());
    }

    @Override
    public Time save(Time time) {
        Long newId = jdbcInsert.executeAndReturnKey(createSqlParameterSource(time)).longValue();

        return new Time(newId, time.getTime());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";

        jdbcTemplate.update(sql, Long.valueOf(id));
    }

}
