package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    boolean deleteById(Long id);
}