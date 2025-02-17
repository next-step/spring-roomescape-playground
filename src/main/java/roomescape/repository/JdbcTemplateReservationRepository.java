package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.List;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("customer_name"),
                    rs.getDate("reservation_date").toLocalDate(),
                    rs.getTime("reservation_time").toLocalTime()
            );

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM RESERVATION", RESERVATION_ROW_MAPPER);
    }
}
