package roomescape.time.repository;

import roomescape.time.domain.Time;

import java.util.List;

public interface TimeRepository {
    List<Time> findAll();
    Time save(Time time);
    void deleteById(Long id);
    Time findById(Long id);

}
