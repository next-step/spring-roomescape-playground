package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public synchronized List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public synchronized Reservation save(Reservation reservation) {
        Reservation savedReservation = reservation.withId(nextId++);
        reservations.add(savedReservation);
        return savedReservation;
    }

    @Override
    public synchronized boolean deleteById(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
