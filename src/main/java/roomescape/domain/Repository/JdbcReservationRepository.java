package roomescape.domain.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Model.Reservation;
import roomescape.domain.exception.NotFoundReservationException;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcReservationRepository implements BasicRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";
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
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getLocalDateTime());
            return ps;
        }, keyHolder);

        long pk = keyHolder.getKey().longValue();
        return new Reservation(pk, reservation.getName(), reservation.getLocalDateTime());
    }

    @Override
    public void deleteReservation(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int updateCount = jdbcTemplate.update(sql, id);
        if (updateCount == 0) {
            throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
        }
    }
}
