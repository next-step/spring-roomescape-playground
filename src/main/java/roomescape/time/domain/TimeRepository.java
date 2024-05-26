package roomescape.time.domain;

import java.util.List;

public interface TimeRepository {

    List<Time> findAll();

    Time save(Time time);

    void deleteById(Long id);
}
