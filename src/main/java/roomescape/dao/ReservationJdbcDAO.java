package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exception.DataInvalidException;


@Repository
public class ReservationJdbcDAO implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = new ReservationRowMapper();

    public ReservationJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {

        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, reservation.getName());
                ps.setDate(2, java.sql.Date.valueOf(reservation.getDate()));
                ps.setTime(3, java.sql.Time.valueOf(reservation.getTime()));
                return ps;
            }, keyHolder);

            long generatedId = keyHolder.getKey().longValue();

            return new Reservation(generatedId, reservation.getName(), reservation.getDate(), reservation.getTime());

        } catch (DataInvalidException e) {
            throw new DataInvalidException(e.getMessage());
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
            throw new DataInvalidException("예약을 찾을 수 없습니다. ID: " + id);
        }
    }

    @Override
    public Reservation getById(int id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new DataInvalidException("해당 ID의 예약을 찾을 수 없습니다 : " + e.getMessage());
        }

    }

}
