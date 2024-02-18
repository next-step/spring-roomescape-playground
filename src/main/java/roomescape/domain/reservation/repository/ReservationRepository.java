package roomescape.domain.reservation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import roomescape.domain.reservation.domain.Reservation;

@Repository
public class ReservationRepository {

    private final static List<Reservation> reservations = new ArrayList<>();
    private final static AtomicLong index = new AtomicLong(1);

    public Reservation addReservation(Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return newReservation;
    }

    public boolean deleteReservation(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public Optional<Reservation> getReservationById(Long id) {
        Optional<Reservation> foundReservation = reservations.stream()
            .filter(reservation -> reservation.getId().equals(id))
            .findFirst();
        return foundReservation;
    }

    public List<Reservation> getAllReservation() {
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.addAll(reservations);
        return reservationList;
    }

}
