package roomescape.reservation.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.presentation.exception.NotFoundReservationException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        Long generatedAutoId = keyHolder.getKey().longValue();
        return new Reservation(generatedAutoId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public Reservation findById(Long reservationId) {
        String sql = "select id, name, date, time from reservation where id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, reservationMapper(), reservationId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException();
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";

        return jdbcTemplate.query(sql, reservationMapper());
    }

    @Override
    public void delete(Long reservationId) {
        Reservation deletedReservation = this.findById(reservationId);

        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, deletedReservation.getId());

    }

    private RowMapper<Reservation> reservationMapper() {
        return ((rs, rowNum) -> {
            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time")
            );
        });
    }
}
