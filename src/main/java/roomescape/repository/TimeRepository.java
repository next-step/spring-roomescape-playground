package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.TimeException;

@Repository
public class TimeRepository {
	private final Map<Long, Time> times;

	private final AtomicLong id;

	public TimeRepository() {
		this.times = new HashMap<>();
		this.id = new AtomicLong(0);
	}

	public Time findById(Long id) {
		if (!times.containsKey(id)) {
			throw new TimeException("Time with id " + id + " not found.");
		}
		return times.get(id);
	}

	public List<Time> findAll() {
		return List.copyOf(times.values());
	}

	public Long save(Time time) {
		if (times.containsKey(time.id())) {
			throw new TimeException("Time with id " + time.id() + " already exists.");
		}

		times.put(time.id(), time);
		return time.id();
	}

	public void deleteById(Long id) {
		if (!times.containsKey(id)) {
			throw new TimeException("Time with id " + id + " not found.");
		}
		times.remove(id);
	}
}
