package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.TimeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeService {

    private final TimeRepository timeRepository;

    public List<TimeResponse> times() {
        return timeRepository.findAll().stream()
            .map(time -> new TimeResponse(time.getId(), time.getTime()))
            .toList();
    }

    @Transactional
    public TimeResponse addTime(TimeRequest request) {
        Time time = timeRepository.save(new Time(null, request.getTime()));
        return new TimeResponse(time.getId(), time.getTime());
    }

    @Transactional
    public void deleteTime(Long id) {
        timeRepository.deleteById(id);
    }
}
