package roomescape.repository.reservation.interfaces;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.domain.time.Time;

public interface TimeRepository {

    Long save(Time time);

    Optional<Time> findById(Long id);

    Optional<Time> findByTime(LocalTime time);

    List<Time> findAll();

    void delete(Long timeId);
}
