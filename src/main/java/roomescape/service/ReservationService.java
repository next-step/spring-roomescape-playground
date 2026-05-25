package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        return reservationRepository.save(reservationRequest);
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }
}
