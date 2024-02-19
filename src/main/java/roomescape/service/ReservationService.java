package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(ReservationRequest reservationRequest) {
        return ReservationResponse.from(reservationRepository.create(reservationRequest));
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
