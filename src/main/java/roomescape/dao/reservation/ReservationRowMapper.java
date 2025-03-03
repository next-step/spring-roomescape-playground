package roomescape.dao.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import roomescape.dao.time.TimeRowMapper;
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
    public Reservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Long timeId = resultSet.getLong("time_id");
        Time time = getTimeById(timeId);

        return new Reservation(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );

    }

    private Time getTimeById(Long timeId) {

        return jdbcTemplate.queryForObject("SELECT * FROM time WHERE id = ?",
                new TimeRowMapper(),
                timeId
        );

    }

}
