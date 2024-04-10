package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.domain.Time;
import roomescape.dto.request.TimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Long createTime(TimeRequest timeRequest) {
        return timeRepository.create(timeRequest);
    }

    public TimeResponse getTime(Long id) {
        Time time = timeRepository.findById(id);
        return TimeResponse.from(time);
    }


    public List<TimeResponse> getTimes() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::from)
                .toList();
    }
}