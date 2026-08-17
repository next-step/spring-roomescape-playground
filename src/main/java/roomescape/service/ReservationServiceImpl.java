package roomescape.service;

import roomescape.dto.request.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepositoryV1;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepositoryV1 reservationRepository;

    public ReservationServiceImpl(ReservationRepositoryV1 reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
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
    public void deleteReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        reservationRepository.delete(reservation);
    }
}
