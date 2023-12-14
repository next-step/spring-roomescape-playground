package roomescape.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
            this.reservationRepository = reservationRepository;
            this.timeRepository = timeRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        return ReservationResponse.from(reservation);
    }

    public Long createReservation(ReservationRequest reservationRequest) {
        Time time = timeRepository.findById(reservationRequest.getTime());
        return reservationRepository.create(reservationRequest, time.getId());
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }
}
