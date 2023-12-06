package roomescape.domain.time.repository;

import roomescape.domain.time.entity.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimesRepository {

    Time save(final LocalTime time);

    List<Time> findAll();

    Optional<Time> findById(final Long id);

    void deleteById(final Long id);

}
