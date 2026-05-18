package roomescape.reservations.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservations.model.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getObject("date", LocalDate.class),
            rs.getObject("time", LocalTime.class)
    );

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAllReservations() {
        String query = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(query, rowMapper);
    }

    public Optional<Reservation> getReservationById(Long id) {
        String query = "SELECT id, name, date, time FROM reservation WHERE id=?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, rowMapper));
    }

    public Long createReservation(Reservation reservation) {
        String query = "INSERT INTO reservation(name, date, time) VALUES(?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setObject(3, reservation.getTime());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int getReservationCountInTimeSlot(LocalDate date, LocalTime time) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ? FOR UPDATE SKIP LOCKED";
        return jdbcTemplate.queryForObject(sql, Integer.class, date, time);
    }

    public int deleteReservationById(Long id) {
        String query = "DELETE FROM reservation WHERE id=?";
        return jdbcTemplate.update(query, id);
    }

    public boolean existsDuplicateReservationWithSameUser(LocalDate date, LocalTime time, String name) {
        String query = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ? and name =?";
        int affectedRowsCount = jdbcTemplate.queryForObject(query, Integer.class, date, time, name);
        return affectedRowsCount > 0;
    }
}
