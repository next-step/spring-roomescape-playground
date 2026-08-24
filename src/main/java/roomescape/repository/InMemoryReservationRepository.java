package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private final AtomicLong id = new AtomicLong(0);
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation saved = reservation.withId(id.incrementAndGet());
        reservations.add(saved);

        return saved;
    }

    @Override
    public boolean deleteById(long id) {
        return reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
