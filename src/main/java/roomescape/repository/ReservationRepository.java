package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

import java.util.List;

@Repository
public class ReservationRepository {
    private final Reservations reservations = new Reservations();

    public List<Reservation> getReservations() {
        return reservations.getReservations();
    }

    public Reservation addReservation(Reservation reservation) {
        return reservations.add(reservation);
    }

    public void deleteReservation(long id) {
        reservations.delete(id);
    }
}
