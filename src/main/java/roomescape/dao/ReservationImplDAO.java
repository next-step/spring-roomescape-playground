package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.Reservation;

public class ReservationImplDAO implements ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationImplDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
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

    }

    @Override
    public int count() {
        String sql = "SELECT count(*) FROM reservation";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        if (count == null) {
            return 0;
        }

        return count;
    }
}
