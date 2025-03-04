package roomescape.dao.time;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.entity.Time;

@Component
public class TimeRowMapper implements RowMapper<Time> {

    @Override
    public Time mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Time(
                resultSet.getLong("id"),
                resultSet.getTime("time").toLocalTime()
        );
    }

}
