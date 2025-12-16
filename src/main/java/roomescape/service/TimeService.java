package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.exception.InvalidReservationException;
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

    public Time save(TimeRequest request) {
        Time newTime = new Time(null, request.time());
        return timeRepository.save(newTime);
    }

    public void deleteById(Long id) {
        boolean deleted = timeRepository.deleteById(id);
        if (!deleted) {
            throw new InvalidReservationException("존재하지 않는 시간입니다.");
        }
    }
}
