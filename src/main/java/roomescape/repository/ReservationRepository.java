package roomescape.repository;

import java.util.List;

import roomescape.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> findAll();

    boolean existsById(Long id);

    Reservation save(Reservation reservation);

    void deleteById(Long id);
}
