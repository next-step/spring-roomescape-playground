package roomescape.repository.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReserveDateTime;
import roomescape.repository.reservation.interfaces.ReservationRepository;

@Repository
public class ReservationDatabaseRepository implements ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
        Long reservationId = resultSet.getLong("id");
        String name = resultSet.getString("name");
        LocalDate reserveDate = resultSet.getDate("reserve_date").toLocalDate();
        LocalTime reserveTime = resultSet.getTime("reserve_time").toLocalTime();
        return new Reservation(reservationId, name, new ReserveDateTime(reserveDate, reserveTime));
    };

    private final JdbcTemplate jdbcTemplate;

    public ReservationDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservations (name, reserve_date, reserve_time) VALUES (?, ?, ?)";
        return null;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = " SELECT id, name, reserve_date, reserve_time FROM reservations WHERE id = ? ";
        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, reserve_date, reserve_time FROM reservations";
        return List.copyOf(jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER));
    }

    @Override
    public void delete(Reservation reservation) {

    }
}
