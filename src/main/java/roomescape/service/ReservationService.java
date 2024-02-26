package roomescape.service;

import java.util.List;

import roomescape.domain.Reservation;

public interface ReservationService {
    List<Reservation> findAll();

    Reservation add(Reservation request);

    void remove(Long id);
}
