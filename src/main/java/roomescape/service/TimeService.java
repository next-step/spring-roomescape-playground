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
        if (timeRepository.existsByTime(time.getTime())) {
            throw new IllegalArgumentException("이미 존재하는 시간대입니다.");
        }

        return timeRepository.save(time);
    }

    public void deleteById(long id) {
        boolean deleted = timeRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundTimeException("해당 id의 시간을 찾을 수 없습니다.");
        }
    }
}
