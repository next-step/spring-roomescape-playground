package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
        resultSet.getLong("id"),
        resultSet.getString("name"),
        resultSet.getDate("date").toLocalDate(),
        resultSet.getTime("time").toLocalTime()
    );

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation", RESERVATION_ROW_MAPPER);
    }
}
