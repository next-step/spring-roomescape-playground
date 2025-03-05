package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.request.CreateTimeRequest;
import roomescape.application.dto.response.TimeResponse;
import roomescape.common.error.ErrorCode;
import roomescape.common.error.exception.InvalidValueException;
import roomescape.domain.time.Time;
import roomescape.repository.reservation.interfaces.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponse> findAll() {
        return getAllTimeResponse();
    }

    public Long saveTime(CreateTimeRequest request) {
        if (isAlreadyExistTime(request)) {
            throw new InvalidValueException(ErrorCode.INVALID_TIME_VALUE);
        }
        Time time = new Time(null, request.time());
        return timeRepository.save(time);
    }

    private List<TimeResponse> getAllTimeResponse() {
        List<Time> foundTimes = timeRepository.findAll();
        return foundTimes.stream()
                .map(this::toDto)
                .toList();
    }

    private boolean isAlreadyExistTime(CreateTimeRequest request) {
        return timeRepository.findByTime(request.time()).isPresent();
    }

    public void deleteTime(Long id) {
        timeRepository.delete(id);
    }

    private TimeResponse toDto(Time time) {
        return new TimeResponse(time.getId(), time.getAvailableTime());
    }
}
