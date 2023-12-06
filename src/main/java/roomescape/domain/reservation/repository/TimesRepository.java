package roomescape.domain.reservation.repository;

import roomescape.domain.reservation.entity.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimesRepository {

    Time save(final LocalTime time);

    List<Time> findAll();

    Optional<Time> findById(final Long id);

    void deleteById(final Long id);

}
