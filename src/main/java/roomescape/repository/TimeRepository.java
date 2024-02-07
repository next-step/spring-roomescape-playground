package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.entity.Time;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public TimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Time> timeRowMapper =
            (resultSet, rowNum) -> {
                return new Time(
                        resultSet.getLong("id"),
                        resultSet.getTime("time").toLocalTime()
                );
            };

    public List<Time> findAll() {
        String sql = "select * from time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Optional<Time> findById(Long id) {
        try {
            String sql = "select * from time where id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, timeRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public Time save(Time time) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(time);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new Time(id, time.getTime());
    }

    public int delete(Long id) {
        String sql = "delete from time where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
