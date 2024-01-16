package roomescape.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRawMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime());
        return reservation;
    };

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservationList = jdbcTemplate.query(sql, reservationRawMapper);

        return reservationList;
    }
}
