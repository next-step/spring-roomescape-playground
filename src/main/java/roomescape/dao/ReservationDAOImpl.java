package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;


@Repository
public class ReservationImplDAO implements ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationImplDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reservation.class));
    }

    @Override
    public void update(Reservation reservation) {
        String sql = "UPDATE reservation SET name = ?, date = ?, time = ? WHERE id = ?";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime(),
                reservation.getId());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public int count() {
        String sql = "SELECT count(*) FROM reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

}
