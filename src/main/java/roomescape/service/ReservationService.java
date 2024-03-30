package roomescape.service;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> reservations() {
        return reservationRepository.findAll();
    }

    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody Reservation request) {

        Reservation reservation = reservationRepository.save(request);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    public ResponseEntity<Object> deleteReservation(Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
