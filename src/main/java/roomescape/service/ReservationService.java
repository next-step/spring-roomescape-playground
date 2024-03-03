package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationDTO;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation makeReservation(ReservationDTO reservationDTO) {
        Time time = timeRepository.findById(reservationDTO.time());
        Reservation reservation = new Reservation(null, reservationDTO.name(), reservationDTO.date(), time);
        //Long id = reservationRepository.makeReservation(reservation);

        Reservation newReservation = reservationRepository.makeReservation(reservation);
        return newReservation;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
