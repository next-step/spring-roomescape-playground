package roomescape.reservation.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.exception.NotExistReservationException;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.id() == null) {
            reservation.setId(idGenerator.incrementAndGet());
        }
        reservations.put(reservation.id(), reservation);
        return reservation;
    }

    @Override
    public void deleteById(Long id) {
        if (!reservations.containsKey(id)) {
            throw new NotExistReservationException("해당 예약을 찾을 수 없습니다.");
        }
        reservations.remove(id);
    }
}
