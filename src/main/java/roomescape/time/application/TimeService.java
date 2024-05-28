package roomescape.time.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.reservation.presentation.exception.NotFoundReservationException;
import roomescape.time.domain.Time;
import roomescape.time.persistence.TimeRepository;
import roomescape.time.presentation.dto.request.TimeSaveRequest;
import roomescape.time.presentation.dto.response.TimeResponse;

@Service
public class TimeService {

	private final TimeRepository timeRepository;

	public TimeService(TimeRepository timeRepository) {
		this.timeRepository = timeRepository;
	}

	public List<TimeResponse> getTimes() {
		return timeRepository.findAll().stream().map(TimeResponse::from).toList();
	}

	public TimeResponse saveTime(TimeSaveRequest request) {
		Time time = timeRepository.save(new Time(request.time()));
		return TimeResponse.from(time);
	}

	public void deleteTime(Long id) {
		if (timeRepository.findById(id) == null) {
			throw new NotFoundReservationException("존재하지 않는 시간정보 입니다.");
		}
		timeRepository.delete(id);
	}
}
