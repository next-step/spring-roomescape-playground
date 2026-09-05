package com.cholog.roomescape.domain.service;

import com.cholog.roomescape.domain.dto.request.ReservationRequest;
import com.cholog.roomescape.domain.entity.Reservation;
import com.cholog.roomescape.domain.entity.Time;
import com.cholog.roomescape.domain.exception.badrequest.TimeNotValidException;
import com.cholog.roomescape.domain.exception.notfound.ReservationNotFoundException;
import com.cholog.roomescape.domain.exception.notfound.TimeNotFoundException;
import com.cholog.roomescape.domain.repository.ReservationRepository;
import com.cholog.roomescape.domain.repository.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {

        long timeId;

        try {
            timeId = Long.parseLong(reservationRequest.time());
        } catch (NumberFormatException e) {
            throw new TimeNotValidException(reservationRequest.time());
        }

        Time foundTime = timeRepository.findById(timeId).orElseThrow(TimeNotFoundException::new);

        Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                foundTime
        );

        return reservationRepository.save(reservation);
    }

    @Transactional
    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        reservationRepository.delete(reservation);
    }
}
