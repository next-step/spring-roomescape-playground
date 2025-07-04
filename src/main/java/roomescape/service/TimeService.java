package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.TimeRepository;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeResponse create(TimeRequest request) {
        Time time = Time.of(request.getTime());
        Time saved = timeRepository.save(time);
        return new TimeResponse(saved);
    }

    public List<TimeResponse> findAll() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::new)
                .toList();
    }

    public void delete(Long id) {
        timeRepository.deleteById(id);
    }
}