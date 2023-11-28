package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

import java.util.List;

@Repository
public class QueryRepository {
    private JdbcTemplate jdbcTemplate;

    public QueryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper  = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
        return reservation;
    };


    public List<Reservation> getAllReservations() {
        String sql = "select id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
    
}
