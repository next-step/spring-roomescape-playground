package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.Time;

import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
        SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value
        FROM reservation r
        INNER JOIN time t ON r.time_id = t.id
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                new Time(
                        rs.getLong("time_id"),
                        rs.getString("time_value")
                )
        ));
    }


    public Reservation insert(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime().getId());

        String query = "SELECT id FROM reservation ORDER BY id DESC LIMIT 1";
        Long id = jdbcTemplate.queryForObject(query, Long.class);

        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
