package roomescape.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Reservation(
                rs.getLong("ID"),
                rs.getString("NAME"),
                rs.getString("DATE"),
                new Time(rs.getLong("TIME_ID"), rs.getString("TIME_VALUE"))
        );
    }
}
