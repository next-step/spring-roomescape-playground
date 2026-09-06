package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    boolean existsByDateAndTimeId(LocalDate date, Long timeId);

    boolean existsByTimeId(Long timeId);

    boolean deleteById(Long id);
}
