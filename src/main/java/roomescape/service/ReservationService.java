package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public Reservation createReservation(ReservationRequest request) {
        Reservation newReservation = new Reservation(
                index.incrementAndGet(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(newReservation);
        return newReservation;
    }

    public void deleteReservation(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
