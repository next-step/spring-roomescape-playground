package roomescape.service;

import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationResponse> findAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::toDto)
                .toList();
    }

    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {

        Reservation createdReservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );

        return reservationRepository.save(createdReservation);
    }

    @Override
    public Void deleteReservation(Long reservationId) {
        reservationRepository.delete(reservationId);
        return null;
    }
}
