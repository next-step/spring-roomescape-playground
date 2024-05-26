package roomescape.reservation.domain;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.exception.NotExistReservationException;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getObject("reservation_date_time", LocalDateTime.class)
        ));
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, reservation_date_time) VALUES (?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setObject(2, reservation.reservationDateTime());
            return ps;
        }, keyHolder);

        long generatedKey = keyHolder.getKey().longValue();
        return new Reservation(generatedKey, reservation.name(), reservation.reservationDateTime());
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int updatedRowCount = jdbcTemplate.update(sql, id);
        if (updatedRowCount == 0) {
            throw new NotExistReservationException("해당 예약을 찾을 수 없습니다.");
        }
    }
}
