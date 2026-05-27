package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime()
    );

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setTime(3, Time.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new Reservation(
                generatedId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int affected = jdbcTemplate.update(sql, id);
        return affected > 0;
    }

    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT count(1) FROM reservation WHERE date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                Date.valueOf(date), Time.valueOf(time));
        return count != null && count > 0;
    }
}
