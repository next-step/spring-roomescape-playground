package roomescape.repository;

import java.util.List;
import roomescape.model.Time;

public interface TimeRepository {

    Time findById(Long id);

    List<Time> findAll();

    Time save(Time time);

    void delete(Long id);

}
