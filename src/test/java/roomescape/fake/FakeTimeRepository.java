package roomescape.fake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import roomescape.domain.time.Time;
import roomescape.repository.reservation.interfaces.TimeRepository;

public class FakeTimeRepository implements TimeRepository {

    private final Map<Long, Time> times = new HashMap<>();


    public Long save(Time time) {
        return 0L;
    }

    public Optional<Time> findById(Long id) {
        return Optional.empty();
    }

    public List<Time> findAll() {
        return List.of();
    }

    public void delete(Long timeId) {
        
    }
}
