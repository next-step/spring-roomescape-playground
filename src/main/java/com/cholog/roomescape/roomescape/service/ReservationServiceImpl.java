package com.cholog.roomescape.roomescape.service;

import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;
import com.cholog.roomescape.roomescape.entity.Reservation;
import com.cholog.roomescape.roomescape.exception.ReservationNotFoundException;
import com.cholog.roomescape.roomescape.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {

        Reservation reservation = ReservationRequest.toReservationWithoutId(reservationRequest);

        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        reservationRepository.delete(reservation);
    }
}
