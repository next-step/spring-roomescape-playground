package roomescape.Repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Exception.NotFoundReservationException;
import roomescape.Entity.Reservation;
import roomescape.Entity.ReservationTime;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        ReservationTime time = new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("time_value"));
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                time
        );
    };

    public List<Reservation> getAllReservations() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r " +
                "INNER JOIN time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Reservation getReservationById(Long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r " +
                "INNER JOIN time as t on r.time_id = t.id " +
                "WHERE r.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
        }
    }

    public long createReservation(Reservation reservation) {
        String insertReservationSql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder reservationKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertReservationSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, reservationKeyHolder);
        return reservationKeyHolder.getKey().longValue();
    }

    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
        }
    }
}
