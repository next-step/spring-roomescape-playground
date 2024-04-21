package roomescape.mapper;

import org.springframework.jdbc.core.RowMapper;
import roomescape.time.domain.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationTimeRowMapper implements RowMapper<Time> {
    @Override
    public Time mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Time(
                rs.getLong("ID"),
                rs.getString("TIME")
        );
    }
}
