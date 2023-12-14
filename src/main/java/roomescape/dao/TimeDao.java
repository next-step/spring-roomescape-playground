package roomescape.dao;

import java.util.List;
import roomescape.domain.Time;

public interface TimeDao {

    Time save(Time time);

    List<Time> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    Time findById(Long time);
}
