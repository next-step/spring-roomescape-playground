package roomescape.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Time;

@Repository
public class TimeDaoImpl implements TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Time> TIME_ROW_MAPPER = ((rs, rowNum) -> new Time(
        rs.getLong("id"),
        rs.getTime("time").toLocalTime()
    ));

    public TimeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Time> readAll() {
        return jdbcTemplate.query("SELECT * FROM time", TIME_ROW_MAPPER);
    }

    @Override
    public Time save(Time time) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(time);
        Number key = simpleJdbcInsert.executeAndReturnKey(source);

        return new Time(key.longValue(), time.getTime());
    }

    @Override
    public boolean existsById(Long id) {
        Boolean result = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT 1 FROM time WHERE id = ?)",
            Boolean.class,
            id
        );
        return result != null && result;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }
}
