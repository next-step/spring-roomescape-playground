package roomescape.reservation;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.exception.NotFoundException;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.time as time_value
                FROM reservation as r
                INNER JOIN time as t ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        rs.getDate("date"),
                        new Time(
                                rs.getLong("time_id"),
                                rs.getString("time_value")
                        )
                )
        );
    }

    public Reservation save(Reservation request) {
        String sql = """
                INSERT INTO reservation(name, date, time_id)
                VALUES (?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, request.getName());
            ps.setDate(2, request.getDate());
            ps.setLong(3, request.getTime().getId());

            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new Reservation(
                id,
                request.getName(),
                request.getDate(),
                request.getTime()
        );
    }

    public void deleteById(Long id) {
        String sql = """
                DELETE FROM reservation
                WHERE id = ?
                """;

        int deleteCount = jdbcTemplate.update(sql, id);

        if (deleteCount == 0) {
            throw new NotFoundException("존재하지 않는 예약입니다.");
        }
    }

    public boolean existsDateAndTime(String date, Long timeId) {
        String sql = """
            SELECT EXISTS (
                SELECT 1
                  FROM reservation
                 WHERE date = ?
                   AND time_id = ?
            )
            """;

        return jdbcTemplate.queryForObject(sql, Boolean.class, date, timeId);
    }

    public boolean existsByTime(Long timeId) {
        String sql = """
            SELECT EXISTS (
                SELECT 1
                  FROM reservation
                 WHERE time_id = ?
            )
            """;

        return jdbcTemplate.queryForObject(sql, Boolean.class, timeId);
    }
}
