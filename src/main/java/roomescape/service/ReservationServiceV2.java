package roomescape.service;

import roomescape.dto.request.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

public class ReservationServiceV2 implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceV2(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {
        return null;
    }

    @Override
    public void deleteReservation(Long reservationId) {

    }
}
