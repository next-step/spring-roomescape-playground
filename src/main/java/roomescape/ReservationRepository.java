package roomescape;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> read() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> Reservation.create(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getObject("date", LocalDate.class),
                rs.getObject("time", LocalTime.class)
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

            ps.setString(1, reservationRequest.getName());
            ps.setObject(2, reservationRequest.getDate());
            ps.setObject(3, reservationRequest.getTime());

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
