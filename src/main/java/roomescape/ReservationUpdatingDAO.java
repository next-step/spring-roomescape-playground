package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationUpdatingDAO {
//    private static ReservationUpdatingDAO reservationUpdatingDAO = null;
    private final JdbcTemplate jdbcTemplate;

    ReservationUpdatingDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Reservation reservation){
        String sql = "INSERT INTO reservations (id, name, date, time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }




}
