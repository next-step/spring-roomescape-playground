package roomescape.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.exception.ReservationException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcTemplateReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> Reservation.of(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime()
    );

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setDate(2, java.sql.Date.valueOf(reservation.getDate()));
            ps.setTime(3, java.sql.Time.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return Reservation.of(generatedId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);
        if (affectedRows == 0) {
            throw new ReservationException("[ERROR] 예약을 찾을 수 없습니다.");
        }
    }
}

