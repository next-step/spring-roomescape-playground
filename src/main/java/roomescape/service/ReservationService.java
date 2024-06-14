package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationRepository;
import roomescape.domain.Reservation;

import java.sql.Time;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public Long createReservation(Reservation reservation) {
        Time time = reservation.getTime();
        reservation.setTime(time);
        return reservationRepository.save(reservation);
    }

    public boolean deleteReservation(long id) {
        // id 존재하면 삭제.
        Reservation reservation = reservationRepository.findById(id);
        if (reservation != null) {
            reservationRepository.delete(id);

        }

        return false;
    }
}
