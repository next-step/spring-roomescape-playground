package roomescape;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.exception.NotFoundException;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Reservation> read() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> Reservation.create(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
            )
        );
    }

    public long createReservation(ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql,
                new String[]{"id"}
            );

            ps.setString(1, reservationRequest.name());
            ps.setString(2, reservationRequest.date());
            ps.setString(3, reservationRequest.time());

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();

    }

    public int deleteReservation(long id) {
        int deletedCount = jdbcTemplate.update(
            "DELETE FROM reservation WHERE id = ?",
            id
        );

        return deletedCount;
    }
}
