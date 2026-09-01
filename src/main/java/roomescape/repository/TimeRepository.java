package roomescape.repository;

import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;

public interface TimeRepository {
    boolean existsByStartAt(LocalTime startAt);

    Time save(Time time);

    List<Time> findAll();
}
