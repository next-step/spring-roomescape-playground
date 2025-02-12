package roomescape.repository.reservation;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.repository.reservation.interfaces.ReservationRepository;

@Repository
public class ReservationDatabaseRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Reservation> findAll() {
        return List.of();
    }

    @Override
    public void delete(Reservation reservation) {

    }
}
