package roomescape.repository.reservation.interfaces;

import java.util.List;
import java.util.Optional;
import roomescape.domain.reservation.Reservation;

public class ReservationDatabaseRepository implements ReservationRepository {


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
