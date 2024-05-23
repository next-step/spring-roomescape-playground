package roomescape.reservation.repository;

import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getId() == 0) {
            reservation.setId(index.incrementAndGet());
        }
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public void deleteById(Long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
