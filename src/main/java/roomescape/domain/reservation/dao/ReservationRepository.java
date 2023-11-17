package roomescape.domain.reservation.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.model.Reservation;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> findAll() {
        return reservations;
    }

    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    public Reservation findById(long reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.isEquals(reservationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
