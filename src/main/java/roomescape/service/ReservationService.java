package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationRepository;
import roomescape.Repository.TimeRepository;
import roomescape.controller.ReservationDto;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final TimeRepository timeRepository;

    public ReservationService(final ReservationRepository reservationRepository, final TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationDto reservation) {
        Time time = timeRepository.findById(reservation.time())
                .orElseThrow(IllegalArgumentException::new);
        Reservation newReservation = new Reservation(null, reservation.name(), reservation.date(), time);
        newReservation.setId(reservationRepository.save(newReservation));
        return newReservation;
    }

    public boolean deleteReservation(long id) {
        Reservation reservation = reservationRepository.findById(id);

        if (reservation != null) {
            reservationRepository.delete(id);
            return true;
        }

        return false;
    }
}
