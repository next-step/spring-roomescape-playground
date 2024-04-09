package roomescape.mapper;

import org.springframework.jdbc.core.RowMapper;
import roomescape.reservation.domain.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getLong("ID"));
        reservation.setName(rs.getString("NAME"));
        reservation.setDate(rs.getString("DATE"));
        reservation.setTime(rs.getString("TIME"));
        return reservation;
    }
}
