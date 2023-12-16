package roomescape.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.IdNotExistException;
import roomescape.exception.TimeNotExistException;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> getAllReservations() {
        return jdbcTemplate.query("""
                SELECT 
                r.id as reservation_id, 
                r.name, 
                r.date, 
                t.id as time_id, 
                t.time as time_value 
            FROM reservation as r inner join time as t on r.time_id = t.id
            """, ((rs, count) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getString("date"),
            new Time(rs.getLong("time_id"), rs.getString("time_value"))
        )));
    }

    public Long saveReservation(Reservation reservation) {
        validateSaveReservation(reservation);
        String time = getTime(reservation);
        SqlParameterSource parameterSource = getReservationParameterSource(reservation);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        reservation.setId(id.longValue());
        reservation.setTimeValue(time);
        return id.longValue();
    }

    private String getTime(Reservation reservation) {
        try {
            String ret = jdbcTemplate.queryForObject("SELECT time FROM time WHERE id = " + reservation.getTimeId(),
                String.class);
            if (ret == null || ret.isEmpty()) {
                throw new TimeNotExistException();
            }
            return ret;
        } catch (EmptyResultDataAccessException e) {
            throw new TimeNotExistException();
        }
    }

    private void validateSaveReservation(Reservation reservation) {
        if (reservation == null || reservation.getName().isEmpty() || reservation.getDate().isEmpty()) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
    }

    public void removeReservation(Long id) {
        if (!hasReservationById(id)) {
            throw new IdNotExistException();
        }
        jdbcTemplate.update("DELETE reservation WHERE id = ?", id);
    }

    private boolean hasReservationById(Long id) {
        return Boolean.TRUE.equals(
            jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM reservation WHERE id = " + id + ")",
                Boolean.class)
        );
    }

    private MapSqlParameterSource getReservationParameterSource(Reservation reservation) {
        return new MapSqlParameterSource()
            .addValue("reservationId", reservation.getId())
            .addValue("name", reservation.getName())
            .addValue("date", reservation.getDate())
            .addValue("timeId", reservation.getTimeId())
            .addValue("timeValue", reservation.getTimeValue());
    }
}
