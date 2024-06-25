package roomescape.reservation.application;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.dto.RequestDto;
import roomescape.time.domain.Time;
import roomescape.time.domain.TimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(@Qualifier("jdbcReservationRepository") ReservationRepository reservationRepository,
                              TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(RequestDto request) {
        Time time = timeRepository.findById(request.getTime());
        Reservation reservation = new Reservation(request.getName(), request.getDate(), time);
        return reservationRepository.save(reservation);
    }

    public void deleteById(final Long id) {
        reservationRepository.deleteReservation(id);
    }
}