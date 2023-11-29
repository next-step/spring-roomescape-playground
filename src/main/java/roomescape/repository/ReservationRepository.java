package roomescape.repository;

import static roomescape.query.ReservationQuery.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
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
        return jdbcTemplate.queryForObject(FIND_BY_ID.getQuery(),
            ((rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"),
                rs.getString("date"), rs.getString("time"))), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE.getQuery(), id);
    }
}
