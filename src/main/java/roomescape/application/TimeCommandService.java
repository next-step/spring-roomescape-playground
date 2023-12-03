package roomescape.application;

import org.springframework.stereotype.Service;
import roomescape.application.dto.TimeCreateRequest;
import roomescape.application.dto.TimeResponse;
import roomescape.domain.repository.TimeRepository;

@Service
public class TimeCommandService {
    private final TimeRepository jdbcTimeRepository;

    public TimeCommandService(final TimeRepository jdbcTimeRepository) {
        this.jdbcTimeRepository = jdbcTimeRepository;
    }

    public TimeResponse addTime(final TimeCreateRequest request) {
        return TimeResponse.from(jdbcTimeRepository.save(TimeCreateRequest.from(request)));
    }

    public void removeTime(final Long id) {
        jdbcTimeRepository.delete(id);
    }
}
