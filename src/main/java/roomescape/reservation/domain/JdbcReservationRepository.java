package roomescape.reservation.domain;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.exception.NotExistReservationException;
import roomescape.time.domain.Time;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT \n"
                + "    r.id as reservation_id, \n"
                + "    r.name, \n"
                + "    r.date, \n"
                + "    t.id as time_id, \n"
                + "    t.time as time_value \n"
                + "FROM reservation as r inner join time as t on r.time_id = t.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                new Time(rs.getLong("time_id"), rs.getString("time_value"))
        ));
    }

    @Override
    public Reservation save(Reservation reservation) {
        final String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setObject(2, reservation.date());
            ps.setLong(3, reservation.time().id());
            return ps;
        }, keyHolder);

        final long generatedKey = keyHolder.getKey().longValue();
        return new Reservation(generatedKey, reservation.name(), reservation.date(), reservation.time());
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
