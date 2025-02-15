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

    public ReservationJdbcDAO(JdbcTemplate jdbcTemplate1) {

        this.jdbcTemplate = jdbcTemplate1;
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
    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new InvalidException("예약을 찾을 수 없습니다. ID: " + id);
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
