package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidException;
import roomescape.exception.NotFoundReservationException;


@Repository
public class ReservationDAOImpl implements ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
        } catch (InvalidException e) {
            throw new InvalidException(e.getMessage());
        }

    }

    @Override
    public List<Reservation> getAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reservation.class));
    }

    @Override
    public void update(Reservation reservation) {
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected == 0) {
            throw new InvalidException("예약을 찾을 수 없습니다. ID: " + id);
        }
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Reservation getById(int id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Reservation.class));
    }

}
