package roomescape.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id, r.name, r.date, t.time FROM reservation r INNER JOIN time t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            Time time = new Time(rs.getLong("time_id"), rs.getString("time"));
            return new Reservation(id, name, date, time);
        });
    }

    public Reservation findById(long id) {
        String sql = "SELECT r.id, r.name, r.date, t.time, t.id AS time_id FROM reservation r INNER JOIN time t ON r.time_id = t.id WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            long reservationId = rs.getLong("id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            Time time = new Time(rs.getLong("time_id"), rs.getString("time"));
            return new Reservation(reservationId, name, date, time);
        });
    }

    public long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public long deleteById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected > 0) {
            return id;
        } else {
            return -1;
        }
    }
}