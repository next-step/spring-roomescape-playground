package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
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
        Time newTime = new Time(null, request.time());
        return timeRepository.save(newTime);
    }

    public void deleteTime(Long id) {
        if (!timeRepository.deleteById(id)) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }
    }
}
