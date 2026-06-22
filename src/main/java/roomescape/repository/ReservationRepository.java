package roomescape.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

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
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getObject("date", LocalDate.class),
            new Time(rs.getLong("time_id"), LocalTime.parse(rs.getString("time_value")))
    );

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Long save(String name, LocalDate date, Long timeId) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setObject(2, date.toString());
            ps.setObject(3, timeId);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int countById(Long id) {
        return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM reservation WHERE id = ?", Integer.class, id);
    }

    public int countByDateAndTimeId(LocalDate date, Long timeId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM reservation WHERE date = ? AND time_id = ?",
                Integer.class, date.toString(), timeId);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
