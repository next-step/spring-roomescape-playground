package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private final Reservations reservations = new Reservations();
    private AtomicLong index = new AtomicLong(0);

    public List<Reservation> getReservations() {
        return reservations.getReservations();
    }

    public Reservation addReservation(Reservation reservation) {
        Reservation newReservation = new Reservation(
                index.incrementAndGet(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        reservations.add(newReservation);
        return newReservation;
    }

    public void deleteReservation(long id) {
        reservations.delete(id);
    }
}
