package roomescape.service;

import java.util.List;

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

    public Reservation addReservation(@Valid @RequestBody Reservation request) {
        return reservationRepository.save(request);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
