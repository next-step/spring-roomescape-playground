package roomescape.domain.reservation.dao;

import static java.util.Collections.unmodifiableList;
import static roomescape.domain.reservation.exception.ReservationException.ErrorCode.NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.domain.reservation.entity.Reservation;

@Repository
public class SimpleReservationRepository implements ReservationRepository {
    private static final AtomicLong INDEX = new AtomicLong(0);

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> findAll() {
        return unmodifiableList(reservations);
    }

    public Reservation save(Reservation reservation) {
        Reservation build = Reservation.builder()
                .id(INDEX.incrementAndGet())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
        reservations.add(build);
        return build;
    }

    public Reservation findById(long reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.isIdEquals(reservationId))
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
