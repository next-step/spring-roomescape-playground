package roomescape.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class ReservationQueryingDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRawMapper = (resultSet, rowNum) -> {
        final Time time = new Time(
            resultSet.getLong("time_id"),
            resultSet.getTime("time_value").toLocalTime());

        Reservation reservation = new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            time);
        return reservation;
    };

    public ReservationQueryingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> selectListReservation() {
        String sql = "SELECT \n"
            + "    r.id as reservation_id, \n"
            + "    r.name, \n"
            + "    r.date, \n"
            + "    t.id as time_id, \n"
            + "    t.time as time_value \n"
            + "FROM reservation as r inner join time as t on r.time_id = t.id\n";

        return jdbcTemplate.query(sql, reservationRawMapper);
    }
}
