package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        return Reservation.of(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getObject("date", LocalDate.class),
                rs.getObject("time", LocalTime.class)
        );
    };

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time", reservation.getTime());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return Reservation.of(
                key.longValue(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE id = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(deleteSql, id);
    }

    public void clear() {
        String sql = "TRUNCATE TABLE reservation";
        jdbcTemplate.update(sql);
    }

    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT count(*) FROM reservation WHERE date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        return count != null && count > 0;
    }

    public Reservation get (String name,LocalDate date, LocalTime time)
    {
        String sql = "SELECT id, name, date, time FROM reservation WHERE name = ? AND date = ? AND time = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, name, date, time);
    }
}
