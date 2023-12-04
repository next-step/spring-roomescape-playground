package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;
import java.sql.PreparedStatement;
import java.time.LocalDate;

@Repository
public class ReservationUpdateRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationUpdateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation insert(String name, LocalDate date, Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time_id) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, name);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setLong(3, time.getId());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, name, date, time);
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }

}
