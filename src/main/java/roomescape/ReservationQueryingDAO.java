package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationQueryingDAO {
//    private static ReservationQueryDAO reservationQuery = null;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        final Long id = resultSet.getLong("id");
        final Reservation reservation = new Reservation(
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
        reservation.setId(id);
        return reservation;
    };
    private final JdbcTemplate jdbcTemplate;

    public ReservationQueryingDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAllReservations(){
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
