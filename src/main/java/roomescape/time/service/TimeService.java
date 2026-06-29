package roomescape.time.service;

import org.springframework.stereotype.Service;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.repository.TimeRepository;

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
        if (timeRepository.existsByTime(request.time())) {
            throw new IllegalArgumentException("이미 존재하는 시간입니다.");
        }

        Time newTime = new Time(null, request.time());
        return timeRepository.save(newTime);
    }

    public void deleteTime(Long id) {
        if (!timeRepository.deleteById(id)) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }
    }
}
