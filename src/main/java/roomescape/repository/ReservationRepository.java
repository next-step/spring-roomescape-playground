package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    public Optional<Reservation> findById(long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst();
    }

    public Reservation save(Reservation reservation) {
        if (reservation.getId() == 0) {
            reservation.setId(index.incrementAndGet());
        }
        reservations.add(reservation);
        return reservation;
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
