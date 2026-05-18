package roomescape.repository;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    boolean deleteById(Long id);

    boolean existsByDateAndTime(LocalDate date, LocalTime time);
}