package roomescape.domain.reservation.dao;

import static roomescape.global.error.exception.ReservationException.ErrorCode.NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.model.Reservation;
import roomescape.global.error.exception.ReservationException;

@Repository
public class SimpleReservationRepository implements ReservationRepository {
    private static final AtomicLong INDEX = new AtomicLong(0);

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> findAll() {
        return reservations;
    }

    public void save(Reservation reservation) {
        reservation.setId(INDEX.incrementAndGet());
        reservations.add(reservation);
    }

    public Reservation findById(long reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.isEquals(reservationId))
                .findFirst()
                .orElseThrow(() -> new ReservationException(NOT_FOUND));
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void deleteById(long reservationId) {
        Reservation reservation = findById(reservationId);
        delete(reservation);
    }

    public void clear() {
        INDEX.set(0);
        reservations.clear();
    }
}
