package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDTO;
import roomescape.dto.ReservationResponseDTO;

import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public ReservationResponseDTO makeReservation(ReservationDTO reservationDTO) {
        return reservationRepository.makeReservation(reservationDTO);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
