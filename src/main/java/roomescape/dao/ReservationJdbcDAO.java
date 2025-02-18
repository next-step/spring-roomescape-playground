package roomescape.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidException;
import roomescape.exception.NotFoundReservationException;


@Repository
public class ReservationJdbcDAO implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = new ReservationRowMapper(); //

    public ReservationJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, ime) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
        } catch (InvalidException e) {
            throw new InvalidException(e.getMessage());
        }

    }

    @Override
    public List<Reservation> getAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Reservation reservation) {
        String sql = "UPDATE reservation SET name = ? , date = ?, time = ? WHERE id = ? ";
        int rowAffected = jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime(),
                reservation.getId());
        if (rowAffected == 0) {
            throw new InvalidException("예약을 찾을 수 없습니다.");
        }
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
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
            if (count == null) {
                return 0;
            }
            return count;
        } catch (Exception e) {
            throw new InvalidException("ID를 조회하는 동안 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public Reservation getById(int id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException("해당 ID의 예약을 찾을 수 없습니다 : " + e.getMessage());
        }

    }

}
