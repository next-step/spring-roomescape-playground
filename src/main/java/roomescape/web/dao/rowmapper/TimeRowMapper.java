package roomescape.web.dao.rowmapper;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TimeRowMapper implements RowMapper<Time> {

    @Override
    public Time mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Time time = new Time();
        time.setId(resultSet.getLong("id"));
        time.setTime(resultSet.getString("time"));
        return time;
    }
}

