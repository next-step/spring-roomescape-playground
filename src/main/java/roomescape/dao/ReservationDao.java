package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationRequest;
import roomescape.model.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getString("date"),
            new Time(rs.getLong("time_id"), rs.getString("time_value"))
    );

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
            SELECT 
                r.id as reservation_id, 
                r.name, 
                r.date, 
                t.id as time_id, 
                t.time as time_value 
            FROM reservation as r 
            INNER JOIN time as t 
            ON r.time_id = t.id
        """;
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation save(ReservationRequest request) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDate());
            ps.setLong(3, request.getTimeId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        String timeQuery = "SELECT time FROM time WHERE id = ?";
        String timeValue = jdbcTemplate.queryForObject(timeQuery, String.class, request.getTimeId());

        return new Reservation(id, request.getName(), request.getDate(), new Time(request.getTimeId(), timeValue));
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
