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


	public Time findByValue(String value) {
		return times.values().stream()
				.filter(time -> time.value().equals(value))
				.findFirst()
				.orElseThrow(() -> new TimeException("Time with value " + value + " not found."));
	}

	public List<Time> findAll() {
		return List.copyOf(times.values());
	}

	public Long save(Time time) {
		Long newId = id.incrementAndGet();
		times.put(newId, time);
		return newId;
	}

	public void deleteById(Long id) {
		if (!times.containsKey(id)) {
			throw new TimeException("Time with id " + id + " not found.");
		}
		times.remove(id);
	}
}
