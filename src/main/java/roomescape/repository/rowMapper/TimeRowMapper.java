package roomescape.repository.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class TimeRowMapper implements RowMapper<Time> {
    @Override
    public Time mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Time(rs.getLong("id")
                , rs.getString("time"));
    }
}
