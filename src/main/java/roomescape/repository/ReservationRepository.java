package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    public Reservation save(Reservation reservation) {
        Reservation saved = new Reservation(
                index.incrementAndGet(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(saved);
        return saved;
    }

    public boolean deleteById(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
