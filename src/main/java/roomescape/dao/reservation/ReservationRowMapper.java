package roomescape.dao.reservation;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Time time = new Time(
                resultSet.getLong("time_id"),
                resultSet.getTime("time_value").toLocalTime()
        );

        return new Reservation(
                resultSet.getInt("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );

    }

}
