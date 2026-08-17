package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepositoryV2 implements ReservationRepository {

    private JdbcTemplate jdbcTemplate;

    public ReservationRepositoryV2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return List.of();
    }

    @Override
    public void delete(Reservation reservation) {

    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return Optional.empty();
    }
}
