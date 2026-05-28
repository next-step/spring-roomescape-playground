package roomescape.reservation;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.reservation.dto.ReservationCreateResponse;
import roomescape.reservation.dto.ReservationSelectResponse;
import roomescape.time.dto.TimeResponse;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<ReservationSelectResponse> findAll() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.time as time_value
                FROM reservation as r inner join time as t on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ReservationSelectResponse(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        new TimeResponse(
                                rs.getLong("time_id"),
                                rs.getString("time_value")
                        )
                )
        );
    }

    public ReservationCreateResponse save(ReservationCreateRequest request) {
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

            ps.setString(1, request.name());
            ps.setDate(2, request.date());
            ps.setLong(3, request.time());

            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new ReservationCreateResponse(
                id,
                request.name(),
                request.date(),
                request.time()
        );
    }

    public void deleteById(Long id) {
        String sql = """
                DELETE FROM reservation
                WHERE id = ?
                """;

        int deleteCount = jdbcTemplate.update(sql, id);

        if (deleteCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }
}
