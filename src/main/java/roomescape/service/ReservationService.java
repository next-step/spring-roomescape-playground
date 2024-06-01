package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.TimeNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeRepository timeRepository;

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
        Time time = reservation.getTime();
        if(!timeRepository.existsById(time.getId())) {
            throw new TimeNotFoundException("Time with id " + time.getId() + " not found");
        }
        reservation.setTime(time);
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
