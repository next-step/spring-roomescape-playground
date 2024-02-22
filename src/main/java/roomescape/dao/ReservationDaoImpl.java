package roomescape.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> new Reservation(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getDate("date").toLocalDate(),
        rs.getTime("time").toLocalTime()
    );

    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> readAll() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation", RESERVATION_ROW_MAPPER);
    }

}
