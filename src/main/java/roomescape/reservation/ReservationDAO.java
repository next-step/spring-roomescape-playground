package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Reservation reservation = new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            rs.getString("time")
                    );
                    return reservation;
                });
    }

}
