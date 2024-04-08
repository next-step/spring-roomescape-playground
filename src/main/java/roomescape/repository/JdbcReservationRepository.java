package roomescape.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        jdbcTemplate.update(sql, request.getName(), request.getDate(), request.getTime());

        Long id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM reservation", Long.class);
        return new Reservation(id, request.getName(), request.getDate(), request.getTime());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        if(jdbcTemplate.update(sql, id) == 0)
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
