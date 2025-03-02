package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.request.CreateTimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.global.exception.BadRequestException;
import roomescape.repository.TimeRepository;

import java.util.List;

import static roomescape.global.exception.ExceptionMessage.TIME_ALREADY_EXISTS;
import static roomescape.global.exception.ExceptionMessage.TIME_NOT_EXISTS;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public TimeResponse createTime(final CreateTimeRequest request) {
        validateDuplication(request);
        final Time time = timeRepository.save(request.toTime());
        return new TimeResponse(time);
    }

    private void validateDuplication(final CreateTimeRequest request) {
        if (timeRepository.existsByTime(request.time())) {
            throw new BadRequestException(TIME_ALREADY_EXISTS.getMessage());
        }
    }

    public List<TimeResponse> getTimes() {
        return timeRepository.findAll()
                .stream()
                .map(TimeResponse::new)
                .toList();
    }

    public void deleteTime(final long timeId) {
        timeRepository.findById(timeId)
                .orElseThrow(() -> new BadRequestException(TIME_NOT_EXISTS.getMessage()));
        timeRepository.deleteById(timeId);
    }
}
