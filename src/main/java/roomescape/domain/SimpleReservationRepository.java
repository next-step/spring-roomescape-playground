package roomescape.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SimpleReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();

    public Reservation save(final Reservation reservation) {
        final Reservation savedReservation = new Reservation(reservations.size() + 1L, reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(savedReservation);
        return savedReservation;
    }

    public List<Reservation> findAll() {
        return reservations;
    }
}
