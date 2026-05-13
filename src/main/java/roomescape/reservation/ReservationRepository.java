package roomescape.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationId;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT id, name, date, time
                FROM reservation
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        new ReservationId(rs.getLong("id")),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                )
        );
    }

    public Reservation save(Reservation reservation) {
        String sql = """
                INSERT INTO reservation(name, date, time)
                VALUES (?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, reservation.name());
            ps.setDate(2, Date.valueOf(reservation.date()));
            ps.setTime(3, Time.valueOf(reservation.time()));

            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new Reservation(
                new ReservationId(id),
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }

    public void deleteById(ReservationId id) {
        String sql = """
                DELETE FROM reservation
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id.id());
    }
}
