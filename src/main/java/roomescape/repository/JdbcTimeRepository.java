package roomescape.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.model.Time;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
public class JdbcTimeRepository implements TimeRepository {

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Time timeAdd(Time time) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(time);
        Number key = jdbcInsert.executeAndReturnKey(param);
        time.setId(key.intValue());
        return time;
    }

    @Override
    public List<Time> findAll() {
        String sql = "select * from time";
        return jdbcTemplate.query(sql, timeRowMapper());
    }

    @Override
    public Time findById(int id) {
        String sql = "select * from time where id = ?";
        Time time = jdbcTemplate.queryForObject(sql, timeRowMapper(), id);
        return time;
    }

    private RowMapper<Time> timeRowMapper() {
        return BeanPropertyRowMapper.newInstance(Time.class);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from time where id = ?", id);
    }
}
