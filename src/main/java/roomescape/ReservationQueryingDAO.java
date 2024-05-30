package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReservationQueryingDAO {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("time_id"),
                resultSet.getString("time_value")
        );

        Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                time
        );
        return reservation;
    };

    public ReservationQueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Reservation> getReservations() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public long createReservation(String name, String date, Long time) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setObject(3, time);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    /**
     *
     * @param id
     * @return delete된 id값
     */

    public int deleteReservationById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
