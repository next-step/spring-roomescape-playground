package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.request.CreateTimeRequest;
import roomescape.application.dto.response.TimeResponse;
import roomescape.domain.time.Time;
import roomescape.repository.reservation.interfaces.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponse> findAll() {
        List<Time> foundTimes = timeRepository.findAll();
        return foundTimes.stream()
                .map(this::toDto)
                .toList();
    }

    public Long saveTime(CreateTimeRequest request) {
        Time time = new Time(null, request.time());
        return timeRepository.save(time);
    }

    public void deleteTime(Long id) {
        timeRepository.delete(id);
    }

    private TimeResponse toDto(Time time) {
        return new TimeResponse(time.getId(), time.getAvailableTime());
    }
}
