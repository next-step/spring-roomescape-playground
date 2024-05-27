package roomescape.domain.Repository.reservationRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Model.Reservation;
import roomescape.domain.Model.Time;
import roomescape.domain.exception.NotFoundReservationException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT " +
                "    r.id as reservation_id, " +
                "    r.name, " +
                "    r.date, " +
                "    t.id as time_id, " +
                "    t.time as time_value " +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                new Time(rs.getLong("time_id"),
                        "time_value"))
        );
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        long pk = keyHolder.getKey().longValue();
        return new Reservation(pk, reservation.getName(), reservation.getDate(),reservation.getTime());
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
