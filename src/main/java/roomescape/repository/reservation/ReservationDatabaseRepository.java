package roomescape.repository.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservedDateTime;
import roomescape.domain.time.Time;
import roomescape.repository.reservation.interfaces.ReservationRepository;

@Repository
public class ReservationDatabaseRepository implements ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
        Long reservationId = resultSet.getLong("reservation_id");
        String name = resultSet.getString("name");
        LocalDate reservedDate = resultSet.getDate("reserved_date").toLocalDate();
        Long timeId = resultSet.getLong("time_id");
        LocalTime reservedTime = resultSet.getTime("available_time").toLocalTime();
        Time time = new Time(timeId, reservedTime);
        return new Reservation(reservationId, name, new ReservedDateTime(reservedDate, time));
    };

    private final JdbcTemplate jdbcTemplate;

    public ReservationDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservations (name, reserved_date, time_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"reservation_id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.reservedDateValue()));
            ps.setLong(3, reservation.getTimeId());
            return ps;
        }, keyHolder);
        long pkValue = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Reservation(pkValue, reservation.getName(),
                reservation.getReservedDateTime());
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = """
                SELECT r.reservation_id, r.name, r.reserved_date, t.time_id, t.available_time
                  FROM reservations as r 
                 INNER JOIN times t ON r.time_id = t.time_id
                 WHERE r.time_id = ?
                """;
        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT r.reservation_id, r.name, r.reserved_date, t.time_id, t.available_time
                  FROM reservations as r 
                 INNER JOIN times t ON r.time_id = t.time_id
                """;
        return Collections.unmodifiableList(jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER));
    }

    @Override
    public void delete(Reservation reservation) {
        String sql = "DELETE FROM reservations WHERE reservation_id = ?";
        jdbcTemplate.update(sql, reservation.getId());
    }

    @Override
    public boolean isExistsByReservedDateAndTime(LocalDate reservedDate, Time time) {
        String sql = """
                SELECT EXISTS (
                    SELECT 1 
                      FROM RESERVATIONS 
                     WHERE reserved_date = ? 
                       AND time_id = ?
                )
                """;
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, reservedDate, time.getId());
        return Boolean.TRUE.equals(result);
    }
}
