package roomescape.fake;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.time.Time;
import roomescape.repository.reservation.interfaces.TimeRepository;

public class FakeTimeRepository implements TimeRepository {

    private final Map<Long, Time> times;
    private final AtomicLong index;

    public FakeTimeRepository() {
        times = new HashMap<>();
        index = new AtomicLong(1);
    }

    public Long save(Time time) {
        Long id = time.getId();
        if (Objects.isNull(id)) {
            time = new Time(index.getAndIncrement(), time.getAvailableTime());
        }
        times.put(id, time);
        return id;
    }

    public Optional<Time> findById(Long id) {
        return Optional.ofNullable(times.get(id));
    }

    @Override
    public Optional<Time> findByTime(LocalTime time) {
        return Optional.empty();
    }

    public List<Time> findAll() {
        return List.copyOf(times.values());
    }

    public void delete(Long timeId) {
        times.remove(timeId);
    }
}
