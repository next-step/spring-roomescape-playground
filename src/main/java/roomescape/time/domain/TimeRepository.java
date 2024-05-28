package roomescape.time.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeRepository{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public TimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Time save(Time time) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(time);
        Long generatedKey = jdbcInsert.executeAndReturnKey(params).longValue();
        return new Time(generatedKey, time.getTime());
    }

    public List<Time> findAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getString("time")
        ));
    }

    public Time findById(final Long id) {
        final String sql = "SELECT * FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getString("time")
        ), id);
    }
    public void delete(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
