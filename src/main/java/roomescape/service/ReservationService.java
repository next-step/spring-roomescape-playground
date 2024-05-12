package roomescape.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

    public ResponseEntity<Reservation> createReservation(Reservation reservation) {
        long reservationId = index.incrementAndGet();
        reservation.setId(reservationId);
        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).body(reservation);
    }

    public ResponseEntity<Void> deleteReservation(long reservationId) {
        Optional<Reservation> reservationOptional = reservations.stream()
                .filter(reservation -> reservation.getId() == reservationId)
                .findFirst();

        if (reservationOptional.isPresent()) {
            reservations.remove(reservationOptional.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
