package roomescape.repository;

import static roomescape.exception.ExceptionMessage.NOT_EXIST_RESERVATION;
import static roomescape.query.ReservationQuery.*;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.exception.BaseException;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
            .withTableName("RESERVATION")
            .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL.getQuery(),
            (rs, rowNum) -> new Reservation(rs.getLong("id"),
                rs.getString("name"), rs.getString("date"), rs.getString("time")));
    }

    public Long create(ReservationRequest reservationRequest) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationRequest);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Reservation findById(Long id) {
        Reservation reservation = jdbcTemplate.queryForObject(FIND_BY_ID.getQuery(),
            ((rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"),
                rs.getString("date"), rs.getString("time"))), id);

        if (reservation == null) {
            throw new BaseException(NOT_EXIST_RESERVATION);
        }
        return reservation;
    }

    public void delete(Long id) {
        findById(id);
        jdbcTemplate.update(DELETE.getQuery(), id);
    }
}
