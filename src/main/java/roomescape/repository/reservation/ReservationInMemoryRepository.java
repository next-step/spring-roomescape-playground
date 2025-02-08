package roomescape.repository.reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.repository.reservation.interfaces.ReservationRepository;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;

    public ReservationInMemoryRepository() {
        this.reservations = new HashMap<>();
        this.index = new AtomicLong(1);
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (Objects.isNull(reservation.getId())) {
            reservation =
                    new Reservation(
                            index.getAndIncrement(),
                            reservation.getName(),
                            reservation.getReserveDate(),
                            reservation.getReserveTime());
        }
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation.getId());
    }
}
