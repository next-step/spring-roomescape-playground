package roomescape.repository;

import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeRepository {
    boolean existsByStartAt(LocalTime startAt);

    Time save(Time time);

    List<Time> findAll();

    boolean deleteById(Long id);

    Optional<Time> findById(Long id);
}
