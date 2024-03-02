package roomescape.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );

        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                time
        );
    }
}
