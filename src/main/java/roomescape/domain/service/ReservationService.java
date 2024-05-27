package roomescape.domain.service;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.Dto.reservationDto.RequestDto;
import roomescape.domain.Model.Reservation;
import roomescape.domain.Model.Time;
import roomescape.domain.Repository.reservationRepository.ReservationRepository;
import roomescape.domain.Repository.timeRepository.TimeRepository;

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