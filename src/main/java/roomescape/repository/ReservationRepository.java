package roomescape.repository;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    Optional<Reservation> findById(long reservationId);

    boolean existsByDateAndTime(LocalDate date, LocalTime time);

    void deleteById(long reservationId);
}
