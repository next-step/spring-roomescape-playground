package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
@RequiredArgsConstructor
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Reservation> findAll() {
        String sql = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.time AS time_value
            FROM reservation AS r 
            INNER JOIN time AS t ON r.time_id = t.id
            """;
        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    @Override
    public Reservation findById(Long id) {
        String sql = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.time AS time_value
            FROM reservation AS r 
            INNER JOIN time AS t ON r.time_id = t.id
            WHERE r.id = ?
            """;
        return jdbcTemplate.queryForObject(sql, reservationRowMapper(), id);
    }

    @Override
    public Long save(Reservation request) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql, new String[] {"id"});
            ps.setString(1, request.getName());
            ps.setObject(2, request.getDate());
            ps.setLong(3, request.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        if (jdbcTemplate.update(sql, id) == 0)
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            new Time(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())
        );
    }
}
