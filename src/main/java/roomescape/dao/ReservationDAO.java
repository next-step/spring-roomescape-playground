package roomescape.dao;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("time_id"),
                resultSet.getTime("time_value").toLocalTime()
        );

        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                time
        );

        return reservation;
    };

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value "
                + "FROM reservation AS r INNER JOIN time AS t "
                + "ON r.time_id = t.id";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Long insertNewReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Long updateReservation(Reservation reservation, Long id) {
        String sql = "UPDATE reservation SET name=?, date=?, time=? WHERE id=?";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime().getTime().toString());
            ps.setString(4, reservation.getId().toString());
            return ps;
        });

        return id;
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
