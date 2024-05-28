package roomescape.time.persistence;

import java.util.List;

import roomescape.time.domain.Time;

public interface TimeRepository {

	List<Time> findAll();

	Time save(Time time);

	Time findById(Long id);

	void delete(Long id);
}
