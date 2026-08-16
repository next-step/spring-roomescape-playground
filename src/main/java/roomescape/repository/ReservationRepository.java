package roomescape.repository;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    boolean deleteById(Long id);

    boolean existsByDateAndTime(LocalDate date, LocalTime time);
}
