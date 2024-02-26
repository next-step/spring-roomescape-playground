package roomescape.web.dao;

import roomescape.domain.Reservation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getLong("id"));
        reservation.setName(rs.getString("name"));
        reservation.setDate(rs.getString("date"));
        reservation.setTime(rs.getString("time"));
        return reservation;
    }
}
