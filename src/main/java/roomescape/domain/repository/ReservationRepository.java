package roomescape.domain.repository;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(final Reservation reservation);

    boolean existsById(final Long id);

    void deleteById(final Long id);
}
