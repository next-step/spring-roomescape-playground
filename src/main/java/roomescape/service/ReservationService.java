package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class ReservationService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        return new Reservation(generatedId, reservation.getName(), reservation.getDate(), reservation.getDate());
    }

    public List<Reservation> getReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ));
    }

    public boolean deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    public boolean isValidReservation(Reservation reservation) {
        return reservation.getName() != null && !reservation.getName().isEmpty() &&
                reservation.getDate() != null && !reservation.getDate().isEmpty() &&
                reservation.getTime() != null && !reservation.getTime().isEmpty();
    }
}