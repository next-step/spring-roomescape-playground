package roomescape.fake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservedDateTime;
import roomescape.repository.reservation.interfaces.ReservationRepository;

public class FakeReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;

    public FakeReservationRepository() {
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
                            new ReservedDateTime(reservation.reservedDateValue(), reservation.reservedTimeValue()));
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
