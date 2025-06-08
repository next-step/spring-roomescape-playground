package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private Long nextId = 1L;

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation(nextId++, reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);
        return newReservation;
    }

    public boolean deleteById(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public void clear() {
        reservations.clear();
        nextId = 1L;
    }
}
