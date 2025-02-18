package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    Optional<Reservation> findById(long reservationId);

    void deleteById(long reservationId);
}
