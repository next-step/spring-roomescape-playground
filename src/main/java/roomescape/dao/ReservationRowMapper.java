package roomescape.dao;

import org.springframework.jdbc.core.RowMapper;
import roomescape.entity.Reservation;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Reservation(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        );
    }
}
