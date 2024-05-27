package roomescape.time.repository;

import roomescape.time.domain.Time;

import java.util.List;

public interface TimeRepositoryImpl {

    List<Time> findAll();

    Time findById(Long id);

    Time save(Time time);

    void deleteById(Long id);
}
