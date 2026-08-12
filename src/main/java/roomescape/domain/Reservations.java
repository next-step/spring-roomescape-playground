package roomescape.domain;

import roomescape.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }

    public Reservation add(Reservation reservation) {
        long newId = index.incrementAndGet();

        Reservation newReservation = new Reservation(
                newId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(newReservation);
        return newReservation;
    }

    public void delete(long id) {
        boolean exists = reservations.stream().anyMatch(reservation -> reservation.getId() == id);

        if (!exists) {
            throw new NotFoundReservationException("해당 id의 예약을 찾을 수 없습니다.");
        }

        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
