package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sqlQuery = "SELECT r.id, r.name, r.date, t.time FROM Reservations AS r INNER JOIN Times AS t ON r.id = t.reservation_id";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> new Reservation(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        ));
    }

    public List<LocalTime> findAllReservationTimesByDate(LocalDate date) {
        String sqlQuery = "SELECT t.time FROM Reservations AS r INNER JOIN Times AS t ON r.id = t.reservation_id WHERE r.date = ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getTime("time").toLocalTime(), date);
    }

    public Reservation saveReservation(Reservation reservation) {
        String insertReservationQuery = "INSERT INTO Reservations(name, date) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertReservationQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            return ps;
        }, keyHolder);

        int latestId = keyHolder.getKey().intValue();

        String insertTimeQuery = "INSERT INTO Times(reservation_id, time) VALUES (?, ?)";
        jdbcTemplate.update(insertTimeQuery, latestId, reservation.getTime());

        return new Reservation(latestId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Reservation findById(int id) {
        String sql = "SELECT r.id, r.name, r.date, t.time " +
                "FROM Reservations r " +
                "INNER JOIN Times t ON r.id = t.reservation_id " +
                "WHERE r.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                int reservationId = rs.getInt("id");
                String name = rs.getString("name");
                java.time.LocalDate date = rs.getDate("date").toLocalDate();
                java.time.LocalTime time = rs.getTime("time").toLocalTime();

                return new Reservation(reservationId, name, date, time);
            }, id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int deleteById(int id) {
        String deleteTimeQuery = "DELETE FROM Times WHERE reservation_id = ?";
        jdbcTemplate.update(deleteTimeQuery, id);

        String deleteReservationQuery = "DELETE FROM Reservations WHERE id = ?";
        return jdbcTemplate.update(deleteReservationQuery, id);
    }
}
