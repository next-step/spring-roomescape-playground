package roomescape.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<Reservation> findReservations() {
        return reservations;
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        long id = index.getAndIncrement();

        Reservation reservation = new Reservation(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );

        reservations.add(reservation);
        return reservation;
    }

    public void deleteReservation(Long id) {
        reservations.removeIf(reservation -> reservation.id().equals(id));
    }
}
