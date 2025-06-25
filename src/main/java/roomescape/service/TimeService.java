package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.exception.InvalidTimeException;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> getAllTimes() {
        return timeRepository.findAll();
    }

    public Time createTime(TimeRequest request) {
        Time time = new Time(null, request.time());
        return timeRepository.save(time);
    }

    public boolean deleteTime(Long id) {
        if(!timeRepository.deleteById(id)) {
            throw new InvalidTimeException("없는 시간입니다." + id);
        }
        return timeRepository.deleteById(id);
    }
}
