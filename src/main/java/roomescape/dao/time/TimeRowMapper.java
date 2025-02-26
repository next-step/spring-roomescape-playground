package roomescape.dao.time;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import roomescape.entity.Time;

public class TimeRowMapper implements RowMapper<Time> {

    @Override
    public Time mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Time(
                rs.getLong("id"),
                rs.getTime("time").toLocalTime()
        );
    }

}
