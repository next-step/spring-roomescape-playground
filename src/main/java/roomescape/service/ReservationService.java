package roomescape.service;

import java.util.List;

import roomescape.domain.Reservation;

public interface ReservationService {
    List<Reservation> findAll();
}
