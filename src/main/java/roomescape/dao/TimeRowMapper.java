package roomescape.dao;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeRowMapper implements RowMapper<Time> {
    @Override
    public Time mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Time(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
    }

}
