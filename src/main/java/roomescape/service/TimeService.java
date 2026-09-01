package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeCreateCommand;
import roomescape.exception.TimeErrorCode;
import roomescape.exception.TimeException;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Time createTime(TimeCreateCommand command) {
        validateNotDuplicate(command.startAt());

        Time time = new Time(command.startAt());
        return timeRepository.save(time);
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public void deleteTime(Long id) {
        boolean deleted = timeRepository.deleteById(id);

        if (!deleted) {
            throw new TimeException(TimeErrorCode.TIME_NOT_FOUND);
        }
    }

    private void validateNotDuplicate(LocalTime startAt) {
        if (timeRepository.existsByStartAt(startAt)) {
            throw new TimeException(TimeErrorCode.TIME_CONFLICT);
        }
    }
}
