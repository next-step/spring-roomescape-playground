package roomescape.repository; // 패키지는 그대로 둡니다.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation; // Reservation 모델 import

import java.sql.PreparedStatement;
import java.util.List;
@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs,rowNum)->{
        return  Reservation.of(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
        );
    };

    public List<Reservation> findAll()
    {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql,reservationRowMapper);
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection ->{
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2,reservation.getDate());
            ps.setString(3,reservation.getName());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return  Reservation.of(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE id = ?";

        Integer count = jdbcTemplate.queryForObject(sql,Integer.class, id);
        return count>0;
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(deleteSql, id);
    }

    public void clear() {
        String sql = "TRUNCATE TABLE reservation";
        jdbcTemplate.update(sql);
    }

    public boolean existsByDateAndTime(String date, String time) {
        String sql = "SELECT count(*) FROM reservations WHERE date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        return count != null && count > 0;
    }

    public boolean exists(String key) {
        String sql = "SELECT count(*) FROM idempotency_keys WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, key);
        return count != null && count > 0;
    }

    public void save(String key) {
        String sql = "INSERT INTO idempotency_keys (id) VALUES (?)";
        jdbcTemplate.update(sql, key);
    }
}
