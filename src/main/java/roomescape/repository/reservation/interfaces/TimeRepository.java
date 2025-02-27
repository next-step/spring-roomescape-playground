package roomescape.repository.reservation.interfaces;

import java.util.List;
import java.util.Optional;
import roomescape.domain.time.Time;

public interface TimeRepository {

    Long save(Time time);

    Optional<Time> findById(Long id);

    List<Time> findAll();

    void delete(Long timeId);
}
