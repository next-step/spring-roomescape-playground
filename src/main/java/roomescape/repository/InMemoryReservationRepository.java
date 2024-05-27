package roomescape.repository;

import roomescape.domain.ReservationEntity;
import roomescape.exception.ReservationNotFoundException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryReservationRepository implements ReservationRepository {

    private final Map<Long, ReservationEntity> reservations = new HashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationEntity> findAll() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public Optional<ReservationEntity> findById(Long id) {
        ReservationEntity reservation = reservations.get(id);
        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }
        return Optional.of(reservation);
    }

    @Override
    public ReservationEntity save(ReservationEntity reservation) {
        long id = index.getAndIncrement();
        ReservationEntity newReservation = new ReservationEntity(id, reservation.name(), reservation.date(), reservation.time());
        reservations.put(id, newReservation);
        return newReservation;
    }

    @Override
    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
