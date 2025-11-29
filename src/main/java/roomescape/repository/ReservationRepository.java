package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, " +
            "t.id as time_id, t.time as time_value " +
            "FROM reservation r " +
            "LEFT JOIN time t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Time time = new Time(rs.getLong("time_id"), rs.getString("time_value"));
            return new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                time
            );
        });
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate());
        params.put("time_id", reservation.getTime());

        Number key = jdbcInsert.executeAndReturnKey(params);
        reservation.setId(key.longValue());
        return reservation;

    }
    public boolean deleteById(Long id){
        String sql= "DELETE From reservation WHERE id = ?";
        int deleteRows = jdbcTemplate.update(sql, id);
        return deleteRows > 0;
    }
}
