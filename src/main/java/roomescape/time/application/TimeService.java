package roomescape.time.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.time.domain.Time;
import roomescape.time.persistence.TimeDAO;
import roomescape.time.presentation.dto.request.TimeSaveRequest;
import roomescape.time.presentation.dto.response.TimeResponse;
import roomescape.time.presentation.exception.NotFoundTimeException;

@Service
public class TimeService {

	private final TimeDAO timeDAO;

	public TimeService(TimeDAO timeDAO) {
		this.timeDAO = timeDAO;
	}

	public List<TimeResponse> getTimes() {
		return timeDAO.findAll().stream().map(TimeResponse::from).toList();
	}

	public TimeResponse saveTime(TimeSaveRequest request) {
		Time time = timeDAO.save(new Time(request.time()));
		return TimeResponse.from(time);
	}

	public void deleteTime(Long id) {
		if (timeDAO.findById(id) == null) {
			throw new NotFoundTimeException("존재하지 않는 시간정보 입니다.");
		}
		timeDAO.delete(id);
	}
}
