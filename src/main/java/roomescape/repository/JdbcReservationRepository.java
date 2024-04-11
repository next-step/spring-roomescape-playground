package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper());
    }

    @Override
    public Reservation save(Reservation request) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql, new String[] {"id"});
            ps.setString(1, request.getName());
            ps.setObject(2, request.getDate());
            ps.setObject(3, request.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, request.getName(), request.getDate(), request.getTime());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        if (jdbcTemplate.update(sql, id) == 0)
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime()
        );
    }
}
