package roomescape.model.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
                    SELECT
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                        t.time as time_value
                    FROM reservation as r INNER JOIN time as t ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
