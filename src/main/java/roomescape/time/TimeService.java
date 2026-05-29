package roomescape.time;

import java.util.Collection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import roomescape.exception.NotFoundException;
import roomescape.time.dto.TimeCreateRequest;
import roomescape.time.dto.TimeResponse;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Collection<TimeResponse> findAll() {
        return timeRepository.findAll();
    }

    public TimeResponse create(TimeCreateRequest request) {
        try {
            return timeRepository.save(request);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 존재하는 시간입니다.");
        }
    }

    public void deleteById(Long id) {
        int deleteCount = timeRepository.deleteById(id);

        if (deleteCount == 0) {
            throw new NotFoundException("존재하지 않는 id입니다.");
        }

    }
}
