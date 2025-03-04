package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.time.request.TimeRequest;
import roomescape.dto.time.response.TimeResponse;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;
import roomescape.repository.TimeDAO;

@Service
public class TimeService {
    private final TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public TimeResponse createTime(TimeRequest request) {
        validateNotNullTime(request.time());
        validateDuplicateTime(request.time());

        Time time = new Time(request.time());
        Time response = timeDAO.createTime(time);
        return new TimeResponse(response.getId(), response.getTime());
    }

    private void validateNotNullTime(LocalTime time) {
        if (time == null) {
            throw new InvalidValueException(ErrorMessage.INVALID_NULL_TIME.getMessage());
        }
    }

    private void validateDuplicateTime(LocalTime time) {
        if (timeDAO.existsTime(time)) {
            throw new InvalidValueException(ErrorMessage.DUPLICATE_TIME.getMessage());
        }
    }

    public List<TimeResponse> findTimes() {
        List<Time> times = timeDAO.findTimes();
        return times.stream()
            .map(time -> new TimeResponse(time.getId(), time.getTime()))
            .toList();
    }

    public void deleteTime(Long timeId) {
        timeDAO.deleteTime(timeId);
    }

    public Time findTimeById(Long timeId) {
        return timeDAO.findTime(timeId);
    }
}
