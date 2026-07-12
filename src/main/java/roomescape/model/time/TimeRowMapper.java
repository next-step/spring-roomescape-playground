package roomescape.model.time;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeRowMapper implements RowMapper<Time> {
    @Override
    public Time mapRow(ResultSet rs, int rowNum) throws SQLException {
        String timeValue = rs.getString("time");
        Time time = new Time(timeValue);
        time.setId(rs.getLong("id"));
        return time;
    }
}
