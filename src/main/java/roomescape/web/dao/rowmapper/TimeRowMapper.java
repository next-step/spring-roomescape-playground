package roomescape.web.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeRowMapper implements RowMapper<Time> {

    @Override
    public Time mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Time time = new Time();
        time.setId(resultSet.getLong("id"));
        time.setTime(resultSet.getString("time"));
        return time;
    }
}

