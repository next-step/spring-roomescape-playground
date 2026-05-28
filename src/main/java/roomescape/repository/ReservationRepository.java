package roomescape.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getObject("date", LocalDate.class),
            rs.getObject("time", LocalTime.class)
    );

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Long save(String name, LocalDate date, LocalTime time) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setObject(2, date);
            ps.setObject(3, time);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int countById(Long id) {
        String sql = "SELECT COUNT(1) FROM reservation WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null ? count : 0;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int countByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT COUNT(1) FROM reservation WHERE date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        return count != null ? count : 0;
    }
}
