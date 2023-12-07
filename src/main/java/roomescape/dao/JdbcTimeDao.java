package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class JdbcTimeDao implements TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Time> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> new Time(
        resultSet.getLong("id"),
        resultSet.getTime("time").toLocalTime()
    );

    public JdbcTimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Time save(Time time) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(time);
        Number key = simpleJdbcInsert.executeAndReturnKey(source);
        return new Time(key.longValue(), time.getTimeValue());
    }

    @Override
    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT id, time FROM time", RESERVATION_ROW_MAPPER);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Long result = jdbcTemplate.queryForObject("select count(*) from time where id = ?", Long.class, id);
        if (result == null) {
            return false;
        }
        return result > 0;
    }

    @Override
    public Time findById(Long timeId) {
        Time result = jdbcTemplate.queryForObject(
            "SELECT id, time FROM time where id = ?",
            RESERVATION_ROW_MAPPER,
            timeId
        );

        if (result == null) {
            throw new IllegalArgumentException("예약이 존재하지않습니다.");
        }

        return result;
    }
}
