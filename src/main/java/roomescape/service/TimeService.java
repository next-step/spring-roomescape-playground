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
    private static final int NO_ROWS = 0;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public Time save(TimeRequest request) {
        Time time = request.toEntity();
        
        return timeRepository.save(time);
    }

    public void deleteById(Long id) {
        int deletedCount = timeRepository.deleteById(id);

        if (deletedCount == NO_ROWS) {
            throw new NotFoundTimeException("삭제할 시간을 찾을 수 없습니다.");
        }
    }
}
