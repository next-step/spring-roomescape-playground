package roomescape.reservation.dao;

import roomescape.reservation.domain.Reservation;

import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void deleteById(Long id);
}
