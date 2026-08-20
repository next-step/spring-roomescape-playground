package roomescape.repository;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation save(String name, LocalDate date, LocalTime time);

    boolean deleteById(long id);
}
