package roomescape.model.reservation;

import org.springframework.jdbc.core.RowMapper;
import roomescape.model.time.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {

        String timeValue = rs.getString("time_value");

        Time time = new Time(timeValue);

        Reservation reservation = new Reservation(
                rs.getString("name"),
                rs.getString("date"),
                time
        );
        reservation.setId(rs.getLong("reservation_id"));
        return reservation;
    }
}
