package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class ReservationQueryingDAO {
//    private static ReservationQueryDAO reservationQuery = null;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        final Long id = resultSet.getLong("id");
        final Long timeId = resultSet.getLong("time_id");
        final String timeValue = resultSet.getString("time_value");

        final Time time = new Time();
        time.setId(timeId);
        time.setTime(timeValue);

        final Reservation reservation = new Reservation(
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );
        reservation.setId(id);
        return reservation;
    };
    private final JdbcTemplate jdbcTemplate;

    public ReservationQueryingDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAllReservations(){
        String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.time as time_value \n" +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
