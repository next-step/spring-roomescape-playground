package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.util.List;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM RESERVATION", reservationRowMapper());
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return ((rs, rowNum) ->
                new Reservation(
                        rs.getLong("id"),
                        rs.getString("customer_name"),
                        rs.getDate("reservation_date").toLocalDate(),
                        rs.getTime("reservation_time").toLocalTime()
                ));
    }
}
