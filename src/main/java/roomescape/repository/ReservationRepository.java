package roomescape.repository;

<<<<<<< HEAD
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dao.ReservationDao;

=======
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.sql.Statement;
>>>>>>> upstream/hapdaypy
import java.util.List;

@Repository
public class ReservationRepository {

<<<<<<< HEAD
    private final ReservationDao reservationDao;

    public ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public boolean existsByDateAndTimeId(String date, Long timeId) {
        return reservationDao.existsByDateAndTimeId(date, timeId);
    }

    public Reservation save(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    public boolean deleteById(Long id) {
        return reservationDao.deleteById(id);
=======
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation ORDER BY id";
        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    public boolean existsByDateAndTime(String date, String time) {
        String sql = "SELECT COUNT(1) FROM reservation WHERE date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        return count != null && count > 0;
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate());
            preparedStatement.setString(3, reservation.getTime());
            return preparedStatement;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("예약 ID 생성에 실패했습니다.");
        }
        return new Reservation(key.longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, id);
        return updatedRows > 0;
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
>>>>>>> upstream/hapdaypy
    }
}
