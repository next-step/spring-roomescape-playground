package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(long id) {
        if(!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException("Reservation with id " + id + " not found");
        }
        return reservationRepository.getReferenceById(id);
    }


    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(long id) {
        if(!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException("Reservation with id " + id + " not found");
        }
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    }
}
