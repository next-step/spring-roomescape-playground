package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private final Reservations reservations = new Reservations();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> getReservations() {
        return reservations.getReservations();
    }

    public Reservation addReservation(String name, LocalDate date, LocalTime time) {
        Reservation newReservation = new Reservation(
                index.incrementAndGet(),
                name,
                date,
                time
        );
        reservations.add(newReservation);
        return newReservation;
    }

    public void deleteReservation(long id) {
        reservations.delete(id);
    }
}
