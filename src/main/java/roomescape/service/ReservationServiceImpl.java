package roomescape.service;

import roomescape.dto.request.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {

        Reservation reservation = ReservationRequest.toReservationWithoutId(reservationRequest);

        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        reservationRepository.delete(reservation);
    }
}
