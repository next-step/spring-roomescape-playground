package roomescape.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationDto;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (ResultSet, rownumber)
            -> new Reservation(
            ResultSet.getInt("id"),
            ResultSet.getString("name"),
            ResultSet.getDate("date").toLocalDate(),
            ResultSet.getTime("time").toLocalTime()
    );

    public List<Reservation> getAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation getReservationById(int id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, reservationRowMapper);
    }
    public void deleteReservationById(int id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
        return reservation;
    }
}