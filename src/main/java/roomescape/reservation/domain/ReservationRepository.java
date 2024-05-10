package roomescape.reservation.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }
}
