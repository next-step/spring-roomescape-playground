package roomescape.dao.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<Reservation> {


    private final JdbcTemplate jdbcTemplate;

    public ReservationRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long timeId = rs.getLong("time_id");
        Time time = getTimeById(timeId);

        return new Reservation(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                time

        );

    }

    private Time getTimeById(Long timeId) {

        return jdbcTemplate.queryForObject("SELECT * FROM time WHERE id = ?", new Object[]{timeId}, (rs, rowNum) ->
                new Time(
                        rs.getLong("id"),
                        rs.getTime("time").toLocalTime()
                )
        );
    }

}
