package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }

    public Reservation addReservation(Reservation reservation) {
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

    public void deleteReservation(long id) {
        boolean exists = reservations.stream().anyMatch(reservation -> reservation.getId() == id);

        if (!exists) {
            throw new NotFoundReservationException("해당 id의 예약을 찾을 수 없습니다.");
        }

        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
