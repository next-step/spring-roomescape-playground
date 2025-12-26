package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        Time time = new Time(
                rs.getLong("time_id"),
                LocalTime.parse(rs.getString("time_value"))
        );
        Theme theme = new Theme(
                rs.getLong("theme_id"),
                rs.getString("theme_name"),
                rs.getString("theme_description"),
                rs.getString("theme_thumbnail")
        );
        return new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getObject("date", LocalDate.class),
                time,
                theme
        );
    };

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, " +
                "t.id as time_id, t.time as time_value, " +
                "th.id as theme_id, th.name as theme_name, th.description as theme_description, th.thumbnail as theme_thumbnail " +
                "FROM reservation as r " +
                "INNER JOIN time as t ON r.time_id = t.id " +
                "INNER JOIN theme as th ON r.theme_id = th.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public boolean existsDateAndTimeAndTheme(LocalDate date, Time time, Theme theme) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time_id = ? AND theme_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time.getId(), theme.getId());
        return count != null && count > 0;
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id, theme_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            ps.setLong(4, reservation.getTheme().getId());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime(), reservation.getTheme());
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int count = jdbcTemplate.update(sql, id);
        return count > 0;
    }
}
