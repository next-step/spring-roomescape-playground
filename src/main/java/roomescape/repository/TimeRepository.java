package roomescape.repository;

import static roomescape.exception.ExceptionMessage.NOT_EXIST_RESERVATION;
import static roomescape.query.TimeQuery.FIND_BY_ID;

import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Time;
import roomescape.dto.request.TimeRequest;
import roomescape.exception.BaseException;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    
    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource())).withTableName("TIME").usingGeneratedKeyColumns("id");
    }
    
    public Long create(TimeRequest timeRequest) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(timeRequest);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Time findById(Long id) {
        Time time = jdbcTemplate.queryForObject(FIND_BY_ID.getQuery(),
                ((rs, rowNum) -> new Time(rs.getLong("id"), rs.getString("time"))), id);

        if (time == null) {
            throw new BaseException(NOT_EXIST_RESERVATION);
        }
        return time;
    }

}
