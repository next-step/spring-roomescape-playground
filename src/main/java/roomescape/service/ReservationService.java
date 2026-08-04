package roomescape.service;

import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAllReservations();

    Reservation createReservation(ReservationRequest reservationRequest);
}
