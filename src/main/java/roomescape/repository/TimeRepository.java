package roomescape.repository;

import roomescape.domain.TimeEntity;

import java.util.List;
import java.util.Optional;

public interface TimeRepository {
    List<TimeEntity> findAll();
    Optional<TimeEntity> findById(Long id);
    TimeEntity save(TimeEntity time);
    void deleteById(Long id);
}
