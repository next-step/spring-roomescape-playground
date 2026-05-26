package roomescape.DAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sqlQuery = "SELECT r.id, r.name, r.date, t.time FROM Reservations AS r INNER JOIN Times AS t ON r.id = t.time_id";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> new Reservation(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        ));
    }

    public List<LocalDate> findAllDates() {
        String sqlQuery = "SELECT r.date FROM Reservations AS r INNER JOIN Times AS t ON r.id = t.time_id";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getDate("date").toLocalDate());
    }

    public List<LocalTime> findAllTimes() {
        String sqlQuery = "SELECT t.time FROM Reservations AS r INNER JOIN Times AS t ON r.id = t.time_id";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getTime("time").toLocalTime());
    }

    public Reservation save(Reservation reservation) {
        String insertReservationQuery = "INSERT INTO Reservations(name, date) VALUES (?, ?)";
        jdbcTemplate.update(insertReservationQuery, reservation.getName(), reservation.getDate());

        String latestIdQuery = "SELECT id FROM Reservations ORDER BY id DESC LIMIT 1";
        Integer latestId = jdbcTemplate.queryForObject(latestIdQuery, Integer.class);

        String insertTimeQuery = "INSERT INTO Times(time_id, time) VALUES (?, ?)";
        jdbcTemplate.update(insertTimeQuery, latestId, reservation.getTime());

        return new Reservation(latestId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public int deleteById(int id) {
        String deleteTimeQuery = "DELETE FROM Times WHERE time_id = ?";
        jdbcTemplate.update(deleteTimeQuery, id);

        String deleteReservationQuery = "DELETE FROM Reservations WHERE id = ?";
        return jdbcTemplate.update(deleteReservationQuery, id);
    }
}
