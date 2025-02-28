package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.mapper.ReservationRowMapper;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;
    private final ReservationRowMapper reservationRowMapper;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDAO(JdbcTemplate jdbcTemplate, ReservationRowMapper reservationRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationRowMapper = reservationRowMapper;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findReservations() {
        String sql = """
                select r.id as reservation_id,
                       r.name,
                       r.date,
                       t.id as time_id,
                       t.time as time_value
                from reservation as r
                inner join time as t on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation createReservation(Reservation reservation) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate());
        params.put("time_id", reservation.getTime().getId());

        long key = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(key, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void deleteReservation(long reservationId) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, reservationId);
    }
}
