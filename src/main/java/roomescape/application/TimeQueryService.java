package roomescape.application;

import org.springframework.stereotype.Service;
import roomescape.application.dto.TimeResponse;
import roomescape.domain.repository.TimeRepository;

import java.util.List;

@Service
public class TimeQueryService {
    private final TimeRepository jdbcTimeRepository;

    public TimeQueryService(final TimeRepository jdbcTimeRepository) {
        this.jdbcTimeRepository = jdbcTimeRepository;
    }

    public List<TimeResponse> findTimes() {
        return TimeResponse.from(jdbcTimeRepository.findAll());
    }
}
