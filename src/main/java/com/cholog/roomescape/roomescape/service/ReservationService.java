package com.cholog.roomescape.roomescape.service;

import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;
import com.cholog.roomescape.roomescape.entity.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAllReservations();

    Reservation createReservation(ReservationRequest reservationRequest);

    void deleteReservation(Long reservationId);
}
