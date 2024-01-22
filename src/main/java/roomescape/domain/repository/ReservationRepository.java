package roomescape.domain.repository;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();
}
