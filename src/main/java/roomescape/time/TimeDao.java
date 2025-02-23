package roomescape.time;

import java.util.List;
import java.util.Optional;

public interface TimeDao {

    Optional<Time> findById(Long id);

    Optional<Time> findByTime(Time time);

    List<Time> findAll();

    Time save(Time time);

    void delete(Long id);
}
