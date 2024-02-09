package roomescape.domain.repository;

import roomescape.domain.Time;

import java.util.List;
import java.util.Optional;

public interface TimeRepository {

    Time save(final Time time);

    List<Time> findAll();

    Optional<Time> findById(final Long timeId);

    boolean existsById(final Long id);

    void deleteById(final Long id);
}
