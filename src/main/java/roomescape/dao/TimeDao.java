package roomescape.dao;

import java.util.List;

import roomescape.domain.Time;

public interface TimeDao {
    List<Time> readAll();

    Time save(Time time);

    boolean existsById(Long id);

    void delete(Long id);

    Time findById(Long id);
}
