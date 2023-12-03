package roomescape.domain.reservation.dao;

import static java.util.Objects.requireNonNull;
import static roomescape.domain.reservation.exception.ReservationException.ErrorCode.NOT_FOUND;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.domain.reservation.entity.Reservation;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, String.valueOf(reservation.getDate()));
            ps.setString(3, String.valueOf(reservation.getTime()));
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
            return jdbcTemplate.queryForObject(sql, new Object[]{reservationId}, new ReservationMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new ReservationException(NOT_FOUND);
        }
    }

    @Override
    public void delete(Reservation reservation) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, reservation.getId());
    }

    @Override
    public void deleteById(long reservationId) {
        Reservation reservation = findById(reservationId);
        delete(reservation);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, new ReservationMapper());
    }

    private static class ReservationMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Reservation.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .date(rs.getDate("date").toLocalDate())
                    .time(rs.getTime("time").toLocalTime())
                    .build();
        }
    }
}
