package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.rowMapper.TimeRowMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;

    public TimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Long saveTime(Time newTime) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(newTime);

        return insert.executeAndReturnKey(param).longValue();
    }

    public List<Time> findAllTime() {
        String sql = "SELECT id, time FROM time";

        return jdbcTemplate.query(
                sql,
                new TimeRowMapper());
    }

    public void deleteTime(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    public Time findById(Long id) {
        String sql = "SELECT * FROM time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                new TimeRowMapper(),
                id);
    }
}
