package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public Reservation save(Reservation reservation) {
        Reservation savedReservation = Reservation.withId(reservation, index.getAndIncrement());
        reservations.add(savedReservation);
        return savedReservation;
    }

    public List<Reservation> find() {
        return reservations;
    }

    public List<Reservation> delete(Reservation reservation) {
        reservations.remove(reservation);
        return reservations;
    }
}
