package roomescape.repository;

import roomescape.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void delete(Reservation reservation);

    Optional<Reservation> findById(Long reservationId);
}
