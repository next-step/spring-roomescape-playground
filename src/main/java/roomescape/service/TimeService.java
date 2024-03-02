package roomescape.service;

import java.util.List;
import roomescape.domain.Time;

public interface TimeService {
	List<Time> getAllTimes();

	Time addTime(String time);

	void deleteTime(Long id);
}
