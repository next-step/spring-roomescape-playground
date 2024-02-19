package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponse> findAll() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::from)
                .toList();
    }

    public TimeResponse create(TimeRequest timeRequest) {
        return TimeResponse.from(timeRepository.create(timeRequest));
    }

    public void delete(Long id) {
        timeRepository.delete(id);
    }
}
