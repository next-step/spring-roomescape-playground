package roomescape.service;

import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationService {

    List<ReservationResponse> findAllReservations();

    Reservation createReservation(ReservationRequest reservationRequest);

    Void deleteReservation(Long reservationId);
}
