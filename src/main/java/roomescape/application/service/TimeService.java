package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.request.CreateTimeRequest;
import roomescape.domain.time.Time;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public Long saveTime(CreateTimeRequest request) {
        Time time = new Time(null, request.time());
        return timeRepository.save(time);
    }

    public void deleteTime(Long id) {
        timeRepository.delete(id);
    }
}
