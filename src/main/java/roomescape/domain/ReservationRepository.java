package roomescape.domain;

import org.springframework.stereotype.Repository;
import roomescape.domain.exception.NoReservationException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public void deleteReservationById(Long id) {
        Optional<Reservation> reservation = reservations.stream()
                .filter(it -> it.getId() == 1)
                .findFirst();
        try {
            reservation.get();
        } catch (NoSuchElementException e) {
            throw new NoReservationException("존재하지 않는 예약입니다.");
        }
    }
}
