package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> getTime() {
        return timeRepository.findAll();
    }

    public Time createTime(TimeRequest timeRequest) {
        Time time = timeRepository.findById(timeRequest.id())
                .orElseThrow(NotFoundTimeException::new
                );
        long id = time.id();
        return new Time(
                id,
                timeRequest.time()
        );
    }

    public void cancelTime(Long id) {
        boolean isRemoved = timeRepository.delete(id);
        if (!isRemoved) {
            throw new NotFoundTimeException();
        }
    }
}
