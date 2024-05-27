package roomescape.service;

import roomescape.dao.ReservationRepository;
import roomescape.domain.Reservation;

import java.util.List;

public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public Reservation insertReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public int deleteReservation(int id){
        return reservationRepository.delete(id);
    }

}
