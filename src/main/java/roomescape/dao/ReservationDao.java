package roomescape.dao;

import java.util.List;

import roomescape.domain.Reservation;

public interface ReservationDao {

    List<Reservation> findAll();

    boolean existsById(Long id);

    Reservation save(Reservation reservation);

    void deleteById(Long id);
}
