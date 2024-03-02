package roomescape.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.TimeException;

@Repository
public class TimeRepository {
	private final Map<Long, Time> times;

	public TimeRepository() {
		this.times = new HashMap<>();
	}

	public Time findById(Long id) {
		if (!times.containsKey(id)) {
			throw new TimeException("Time with id " + id + " not found.");
		}
		return times.get(id);
	}

	public void save(Time time) {
		if (times.containsKey(time.time_id())) {
			throw new TimeException("Time with id " + time.time_id() + " already exists.");
		}
		times.put(time.time_id(), time);
	}

	public void deleteById(Long id) {
		if (!times.containsKey(id)) {
			throw new TimeException("Time with id " + id + " not found.");
		}
		times.remove(id);
	}
}
