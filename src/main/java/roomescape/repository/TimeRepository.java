package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.BaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static roomescape.exception.ExceptionMessage.NOT_EXIST_TIME;


@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final String SELECT_QUERY = """
            SELECT id, time FROM time
            """;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("TIME")
                .usingGeneratedKeyColumns("id");
    }

    public Time findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + " WHERE id = ?",
                    (rs, rowNum) -> mapTime(rs), id);
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException(NOT_EXIST_TIME);
        }
    }

    public List<Time> findAll() {
        return jdbcTemplate.query(SELECT_QUERY, (rs, rowNum) -> mapTime(rs));
    }

    public void deleteTime(Long id) {
        findById(id);
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }

    public Long create(String time) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("time", time);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    private Time mapTime(ResultSet rs) throws SQLException {
        return new Time(rs.getLong("id"), rs.getString("time"));
    }
}
