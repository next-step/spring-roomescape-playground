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
        String sql = """
            SELECT
                r.id AS reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.time AS time_value
            FROM reservation AS r
            INNER JOIN time AS t
                ON r.time_id = t.id
            """;
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {
                Time time = Time.create(
                    rs.getLong("time_id"),
                    rs.getObject("time_value", LocalTime.class)
                );

                return Reservation.create(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    rs.getObject("date", LocalDate.class),
                    time
                );
            }
        );
    }

    public long createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql,
                new String[]{"id"}
            );

            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());

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
