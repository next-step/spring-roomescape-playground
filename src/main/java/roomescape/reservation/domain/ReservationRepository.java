package roomescape.reservation.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }

    public Reservation save(Reservation reservation) {
        if (reservation.id() == null) {
            reservation.setId(idGenerator.incrementAndGet());
        }
        reservations.put(reservation.id(), reservation);
        return reservation;
    }

    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
