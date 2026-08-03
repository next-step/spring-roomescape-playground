package roomescape.service;

import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

public class ReservationServiceV1 implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceV1(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
