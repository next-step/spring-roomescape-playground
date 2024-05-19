package roomescape.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public List<Reservation> findAll() {
        return reservations;
    }
}
