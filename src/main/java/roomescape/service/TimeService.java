package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.request.CreateTimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public TimeResponse createTime(final CreateTimeRequest request) {
        Time time = timeRepository.save(request.toTime());
        return new TimeResponse(time);
    }
}
