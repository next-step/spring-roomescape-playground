package roomescape.dao;

import java.util.List;

import roomescape.domain.Time;

public interface TimeDao {
    List<Time> findAll();

    Time save(Time time);

    boolean existsById(Long id);

    void deleteById(Long id);

    Time findById(Long id);
}
