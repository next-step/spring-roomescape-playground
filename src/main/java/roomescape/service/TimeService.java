package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponse> getAll() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::from)
                .toList();
    }

    public TimeResponse insertTime(TimeRequest timeRequest) {
        Time time = Time.from(timeRequest.time());
        if (timeRepository.existsByTime(timeRequest.time())) {
            throw new IllegalArgumentException("시간 중복");
        }
        return TimeResponse.from(timeRepository.insert(time));
    }

    public void deleteTime(Long id) {
        Time time = timeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("시간 탐색 실패"));
        timeRepository.delete(time);
    }
}
