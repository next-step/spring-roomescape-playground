package roomescape.repository.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
      /*  return new Reservation(rs.getLong("id")
                , rs.getString("name")
                , rs.getDate("date").toLocalDate()
                , new Time(rs.getTime("time").toLocalTime());*/
        return null;
    }
}
