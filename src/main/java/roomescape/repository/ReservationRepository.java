package roomescape.repository;

import static roomescape.exception.ExceptionMessage.NOT_EXIST_RESERVATION;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import static roomescape.query.ReservationQuery.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.BaseException;
import roomescape.dto.request.ReservationRequest;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
    public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
           SELECT r.id as id,
           r.name as name, r.date as date, t.id as time_id, t.time as times
           FROM reservation as r inner join time as t on r.time_id = t.id
           """,
                (rs, rowNum) -> new Reservation(rs.getLong("id"),
                        rs.getString("name"), rs.getString("date"), new Time(rs.getLong("time_id"), rs.getString("times"))));
    }

    public Long create(String name, String date, Long timeId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("date", date)
                .addValue("time_id", timeId);
        return jdbcTemplate.query(FIND_ALL.getQuery(),
                (rs, rowNum) -> new Reservation(rs.getLong("id"),
                        rs.getString("name"), rs.getString("date"), rs.getString("time")));
    }

    public Long create(ReservationRequest reservationRequest) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationRequest);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Reservation findById(Long id) {
        Reservation reservation;
        try {
            reservation = jdbcTemplate.queryForObject("""
            SELECT r.id as id,
            r.name as name, r.date as date, t.id as time_id, t.time as times
            FROM reservation as r inner join time as t on r.time_id = t.id WHERE r.id = ?
            """,
                    ((rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"),
                            rs.getString("date"), new Time(rs.getLong("time_id"), rs.getString("times")))), id);
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException(NOT_EXIST_RESERVATION);
        }
        return reservation;
    }

    public void delete(Long id) {
        findById(id);
        jdbcTemplate.update("""
        delete from reservation where id = ?
        """, id);
        return jdbcTemplate.queryForObject(FIND_BY_ID.getQuery(),
                ((rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"),
                        rs.getString("date"), rs.getString("time"))), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE.getQuery(), id);
    }
}