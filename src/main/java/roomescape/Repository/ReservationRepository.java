package roomescape.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.List;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationMapper = (ResultSet rs, int rowNum) -> {
        Long id = rs.getLong("reservation_id");
        String name = rs.getString("name");
        String date = rs.getString("date");
        Time time = rs.getTime("time_value");
        return new Reservation(id, name, date, time);
    };

    public List<Reservation> findAll() {
        String query = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r INNER JOIN time as t ON r.time_id = t.id";
        return jdbcTemplate.query(query, reservationMapper);
    }

    public Long save(Reservation reservation) {
        String query = "INSERT INTO reservations (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(Long reservationId) {
        String query = "DELETE FROM reservations WHERE id = ?";
        jdbcTemplate.update(query, reservationId);
    }

    public Reservation findById(Long id) {
        String query = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r INNER JOIN time as t ON r.time_id = t.id " +
                "WHERE r.id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, reservationMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
