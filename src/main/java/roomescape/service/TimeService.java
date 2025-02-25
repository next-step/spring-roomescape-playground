package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.time.request.TimeCreateRequest;
import roomescape.dto.time.response.TimeCreateResponse;
import roomescape.repository.TimeDAO;

@Service
public class TimeService {
    private final TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public TimeCreateResponse createTime(TimeCreateRequest request) {
        Time time = new Time(request.time());
        Time response = timeDAO.createTime(time);
        return new TimeCreateResponse(response.getId(), response.getTime());
    }
}
