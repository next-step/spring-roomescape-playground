package roomescape.mapper;

import org.springframework.jdbc.core.RowMapper;
import roomescape.time.domain.ReservationTime;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationTimeRowMapper implements RowMapper<ReservationTime> {
    @Override
    public ReservationTime mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReservationTime(
                rs.getLong("ID"),
                rs.getString("TIME")
        );
    }
}
