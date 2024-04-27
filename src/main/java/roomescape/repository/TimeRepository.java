package roomescape.repository;

import java.util.List;

import roomescape.domain.Time;

public interface TimeRepository {
    List<Time> findAll();

    Time save(Time request);

    void deleteById(Long id);
}
