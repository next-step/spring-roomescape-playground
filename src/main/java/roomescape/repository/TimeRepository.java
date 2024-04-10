package roomescape.repository;

import static roomescape.exception.ExceptionMessage.NOT_EXIST_TIME;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Time;
import roomescape.exception.BaseException;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("TIME")
                .usingGeneratedKeyColumns("id");
    }

//    public Long create(TimeRequest timeRequest) {
//        SqlParameterSource params = new BeanPropertySqlParameterSource(timeRequest);
//        return jdbcInsert.executeAndReturnKey(params).longValue();
//    }

    public Time findById(Long id) {
        Time time;
        try {
            time = jdbcTemplate.queryForObject("""
        SELECT id, time FROM time WHERE id = ?
        """,
                    ((rs, rowNum) -> new Time(rs.getLong("id"), rs.getString("time"))), id);
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException(NOT_EXIST_TIME);
        }
        return time;
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("""
        SELECT id, time FROM time
        """,
                (rs, rowNum) -> new Time(rs.getLong("id"),
                        rs.getString("time")));
    }

    public void deleteTime(Long id) {
        findById(id);
        jdbcTemplate.update("""
        delete from time where id = ?
        """, id);
    }

    public Long create(String time) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("time", time);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }
}