package roomescape.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.exception.customexception.TimeAlreadyExistsException;
import roomescape.exception.customexception.TimeNotFoundException;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.repository.TimeRepository;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public synchronized TimeResponse createTime(TimeRequest timeRequest) {
        Time time = timeRequest.toTime();
        checkConflict(time);
        Time createdTime = timeRepository.saveTime(time);
        return TimeResponse.fromTime(createdTime);
    }

    public List<TimeResponse> readAllTimes() {
        List<Time> times = timeRepository.findAllTimes();
        return times.stream()
                .map(TimeResponse::fromTime)
                .toList();
    }

    public void deleteTime(Long id) {
        int affectedRows = timeRepository.deleteTimeById(id);
        if (affectedRows == 0) {
            throw new TimeNotFoundException();
        }
    }

    private void checkConflict(Time newTime) {
        if (timeRepository.countConflictingTimes(newTime.getTime()) > 0) {
            throw new TimeAlreadyExistsException();
        }
    }
}
