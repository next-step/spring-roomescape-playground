package com.cholog.roomescape.domain.service;

import com.cholog.roomescape.domain.dto.request.ReservationRequest;
import com.cholog.roomescape.domain.entity.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAllReservations();

    Reservation createReservation(ReservationRequest reservationRequest);

    void deleteReservation(Long reservationId);
}
