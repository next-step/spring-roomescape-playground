package roomescape.repository;

import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();
}
