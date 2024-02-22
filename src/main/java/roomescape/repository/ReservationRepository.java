package roomescape.repository;

import java.util.List;

import roomescape.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> findAll();
}
