package roomescape.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.web.dao.rowmapper.ReservationRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcReservationDao implements ReservationDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> getAllReservations() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    @Override
    public Reservation createReservation(Long id, String name, String date, String time) {
        String sql = "INSERT INTO reservation (id, name, date, time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, id,  name, date, time);

        String selectSql = "SELECT * FROM reservation WHERE name = ? AND date = ? AND time = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{name, date, time}, new ReservationRowMapper());
    }

    @Override
    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, new ReservationRowMapper()));
    }

}
