package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;
import roomescape.Exception.BaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static roomescape.Exception.ExceptionMessage.NOT_EXIST_RESERVATION;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final String SELECT_QUERY = """
           SELECT r.id as id,
                  r.name as name,
                  r.date as date,
                  t.id as time_id,
                  t.time as times
           FROM reservation as r
           INNER JOIN time as t ON r.time_id = t.id
           """;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(SELECT_QUERY,
                (rs, rowNum) -> mapReservation(rs));
    }

    public Long create(String name, String date, Long timeId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("date", date)
                .addValue("time_id", timeId);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Reservation findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_QUERY + " WHERE r.id = ?",
                    (rs, rowNum) -> mapReservation(rs), id);
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException(NOT_EXIST_RESERVATION);
        }
    }

    public void delete(Long id) {
        findById(id);
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    private Reservation mapReservation(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                new Time(rs.getLong("time_id"), rs.getString("times"))
        );
    }
}

