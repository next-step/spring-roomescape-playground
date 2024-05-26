package roomescape.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.time FROM reservation r INNER JOIN time t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToReservation(rs));
    }

    public Reservation findById(long id) {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.time FROM reservation r INNER JOIN time t ON r.time_id = t.id WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> mapRowToReservation(rs));
    }

    public long save(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTime().getId());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return key.longValue();
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

    private Reservation mapRowToReservation(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String date = rs.getString("date");
        long timeId = rs.getLong("time_id");
        Time time = new Time(timeId, rs.getTime("time").toLocalTime());
        return new Reservation(id, name, date, time);
    }
}