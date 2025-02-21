package roomescape.repository.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
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
import roomescape.repository.reservation.interfaces.ReservationRepository;

@Repository
public class ReservationDatabaseRepository implements ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
        Long reservationId = resultSet.getLong("reservation_id");
        String name = resultSet.getString("name");
        LocalDate reservedDate = resultSet.getDate("reserved_date").toLocalDate();
        LocalTime reservedTime = resultSet.getTime("reserved_time").toLocalTime();
        return new Reservation(reservationId, name, new ReservedDateTime(reservedDate, reservedTime));
    };

    private final JdbcTemplate jdbcTemplate;

    public ReservationDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservations (name, reserved_date, reserved_time) VALUES (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"reservation_id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.reservedDateValue()));
            ps.setTime(3, Time.valueOf(reservation.reservedTimeValue()));
            return ps;
        }, keyHolder);
        long pkValue = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Reservation(pkValue, reservation.getName(),
                reservation.getReservedDateTime());
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT reservation_id, name, reserved_date, reserved_time FROM reservations WHERE reservation_id = ?";
        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT reservation_id, name, reserved_date, reserved_time FROM reservations";
        return Collections.unmodifiableList(jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER));
    }

    @Override
    public void delete(Reservation reservation) {
        String sql = "DELETE FROM reservations WHERE reservation_id = ?";
        jdbcTemplate.update(sql, reservation.getId());
    }
}
