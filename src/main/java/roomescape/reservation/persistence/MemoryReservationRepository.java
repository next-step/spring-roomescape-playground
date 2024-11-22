package roomescape.reservation.persistence;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.presentation.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository {

    private static Map<Long, Reservation> reservationStore = new ConcurrentHashMap<>();
    private static AtomicLong index = new AtomicLong(1);

    @Override
    public Reservation save(Reservation reservation) {

        Long reservationId = index.getAndIncrement();
        reservation.setId(reservationId);
        reservationStore.put(reservationId, reservation);

        return reservation;
    }

    @Override
    public Reservation findById(Long reservationId) {
        Reservation reservation = reservationStore.get(reservationId);
        if (reservation == null) {
            throw new NotFoundReservationException();
        }
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservationStore.values());
    }

    @Override
    public void delete(Long reservationId) {
        Reservation deletedReservation = this.findById(reservationId);
        reservationStore.remove(deletedReservation.getId());
    }
}
