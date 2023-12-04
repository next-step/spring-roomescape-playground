package roomescape.domain.reservation.dao;

import static java.util.Objects.requireNonNull;
import static roomescape.domain.reservation.exception.ReservationException.ReservationErrorCode.NOT_FOUND;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.domain.reservation.mapper.ReservationMapper;

public class JdbcReservationRepository implements ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, String.valueOf(reservation.getDate()));
            ps.setString(3, String.valueOf(reservation.getTime().getId()));
            return ps;
        }, keyHolder);

        return Reservation.builder()
                .id(requireNonNull(keyHolder.getKey()).longValue())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
    }

    @Override
    public Reservation findById(long reservationId) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{reservationId}, reservationMapper);
        } catch (EmptyResultDataAccessException ex) {
            throw new ReservationException(NOT_FOUND);
        }
    }

    @Override
    public void delete(Reservation reservation) {
        Reservation findReservation = findById(reservation.getId());
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, findReservation.getId());
    }

    @Override
    public void deleteById(long reservationId) {
        Reservation reservation = findById(reservationId);
        delete(reservation);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time_id FROM reservation";
        return jdbcTemplate.query(sql, reservationMapper);
    }
}
