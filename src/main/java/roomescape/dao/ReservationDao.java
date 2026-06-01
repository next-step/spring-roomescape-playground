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
        String sqlQuery = "SELECT r.id, r.name, r.date, t.time FROM Reservations AS r INNER JOIN Times AS t ON r.time_id = t.time_id";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> new Reservation(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        ));
    }

    public List<LocalTime> findAllReservationTimesByDate(LocalDate date) {
        String sqlQuery = "SELECT t.time FROM Reservations AS r INNER JOIN Times AS t ON r.time_id = t.time_id WHERE r.date = ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getTime("time").toLocalTime(), date);
    }

    public Reservation saveReservation(Reservation reservation) {
        String findTimeIdSql = "SELECT time_id FROM Times WHERE time = ?";
        Integer timeId = jdbcTemplate.queryForObject(findTimeIdSql, Integer.class, reservation.getTime());

        String insertReservationQuery = "INSERT INTO Reservations(name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertReservationQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setInt(3, timeId);
            return ps;
        }, keyHolder);

        int latestId = keyHolder.getKey().intValue();
        return new Reservation(latestId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Reservation findById(int id) {
        String sql = "SELECT r.id, r.name, r.date, t.time FROM Reservations r INNER JOIN Times t ON r.time_id = t.time_id WHERE r.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime()
            ), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<LocalTime> findAllTimes() {
        String sql = "SELECT time FROM Times ORDER BY time ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getTime("time").toLocalTime());
    }

    public LocalTime saveTime(LocalTime time) {
        String sql = "INSERT INTO Times(time) VALUES (?)";
        jdbcTemplate.update(sql, time);
        return time;
    }

    public void deleteTimeById(int timeId) {
        String sql = "DELETE FROM Times WHERE time_id = ?";
        jdbcTemplate.update(sql, timeId);
    }

    public int updateReservationTime(int id, LocalDate date, LocalTime time) {
        String findTimeIdSql = "SELECT time_id FROM Times WHERE time = ?";
        Integer timeId = jdbcTemplate.queryForObject(findTimeIdSql, Integer.class, time);

        String updateReservationSql = "UPDATE Reservations SET date = ?, time_id = ? WHERE id = ?";
        return jdbcTemplate.update(updateReservationSql, date, timeId, id);
    }

    public int deleteById(int id) {
        String deleteReservationQuery = "DELETE FROM Reservations WHERE id = ?";
        return jdbcTemplate.update(deleteReservationQuery, id);
    }
}
