package roomescape.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationResponse;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<ReservationResponse> findAll() {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.time FROM reservation r INNER JOIN time t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToReservationResponse(rs));
    }

    public ReservationResponse findById(long id) {
        String sql = "SELECT r.id, r.name, r.date, t.id AS time_id, t.time FROM reservation r INNER JOIN time t ON r.time_id = t.id WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> mapRowToReservationResponse(rs));
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
        return rowsAffected > 0 ? id : -1;
    }

    private ReservationResponse mapRowToReservationResponse(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        LocalDate date = rs.getDate("date").toLocalDate();
        long timeId = rs.getLong("time_id");
        LocalTime time = rs.getTime("time").toLocalTime();
        return new ReservationResponse(id, name, date, timeId, time);
    }
}