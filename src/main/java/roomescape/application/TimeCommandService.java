package roomescape.application;

import org.springframework.stereotype.Service;
import roomescape.application.dto.TimeCreateRequest;
import roomescape.application.dto.TimeResponse;
import roomescape.domain.Time;
import roomescape.domain.repository.TimeRepository;

import java.time.LocalTime;

@Service
public class TimeCommandService {
    private final TimeRepository jdbcTimeRepository;

    public TimeCommandService(final TimeRepository jdbcTimeRepository) {
        this.jdbcTimeRepository = jdbcTimeRepository;
    }

    public TimeResponse addTime(final TimeCreateRequest request) {
        final Time time = new Time.TimeBuilder()
                .time(LocalTime.parse(request.getTime()))
                .build();
        return TimeResponse.from(jdbcTimeRepository.save(time));
    }

    public void removeTime(final Long id) {
        jdbcTimeRepository.delete(id);
    }
}
