package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidReservationException;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(ReservationRequest request) {
        Reservation newReservation = new Reservation(null, request.name(), request.date(), request.time());
        return reservationRepository.save(newReservation);
    }

    public void deleteById(Long id) {
        boolean deleted = reservationRepository.deleteById(id);
        if (!deleted) {
            throw new InvalidReservationException("존재하지 않는 예약입니다.");
        }
    }
}
