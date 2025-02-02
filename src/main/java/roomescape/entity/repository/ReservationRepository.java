package roomescape.entity.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Reservation(
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")));
    }

    public Reservation save(Reservation reservation) {
        String insertQuery = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertQuery, reservation.getName(), reservation.getDate(), reservation.getTime());

        String selectSql = "SELECT id, name, date, time FROM reservation WHERE name = ? AND date = ? AND time = ? ORDER BY id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(selectSql, (rs, rowNum) ->
            new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
            ), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
