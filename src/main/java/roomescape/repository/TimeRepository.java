package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface TimeRepository {

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(Long id);

    boolean existsByTime(LocalTime time);

    ReservationTime save(ReservationTime reservationTime);

    boolean deleteById(Long id);
}
