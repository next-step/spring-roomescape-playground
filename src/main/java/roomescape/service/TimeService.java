package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.TimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponse> getAllTimes() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public TimeResponse createTime(TimeRequest request) {
        Time time = new Time(null, request.time());
        Time savedTime = timeRepository.save(time);
        return TimeResponse.from(savedTime);
    }

    @Transactional
    public void deleteTime(Long id) {
        timeRepository.deleteById(id);
    }
}
