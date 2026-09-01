package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public Time save(Time time) {
        return timeRepository.save(time);
    }

    public void deleteById(long id) {
        boolean deleted = timeRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundTimeException("해당 id의 시간을 찾을 수 없습니다.");
        }
    }
}
