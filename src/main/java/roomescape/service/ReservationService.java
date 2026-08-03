package roomescape.service;

import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAllReservations();

    Reservation createReservation(Reservation reservation);
}
