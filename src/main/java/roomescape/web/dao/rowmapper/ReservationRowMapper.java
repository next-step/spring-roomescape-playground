package roomescape.web.dao.rowmapper;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getLong("reservation_id"));
        reservation.setName(rs.getString("name"));
        reservation.setDate(rs.getString("date"));

        Time time = new Time();
        time.setId(rs.getLong("time_id"));
        time.setTime(rs.getString("time_value"));

        reservation.setTime(time);
        return reservation;
    }
}
