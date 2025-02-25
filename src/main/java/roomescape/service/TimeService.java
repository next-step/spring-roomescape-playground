package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.time.request.TimeRequest;
import roomescape.dto.time.response.TimeResponse;
import roomescape.repository.TimeDAO;

@Service
public class TimeService {
    private final TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public TimeResponse createTime(TimeRequest request) {
        Time time = new Time(request.time());
        Time response = timeDAO.createTime(time);
        return new TimeResponse(response.getId(), response.getTime());
    }

    public List<TimeResponse> findTimes() {
        List<Time> times = timeDAO.findTimes();
        return times.stream()
            .map(time -> new TimeResponse(time.getId(), time.getTime()))
            .toList();
    }
}
