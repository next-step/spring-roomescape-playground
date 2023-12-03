package roomescape.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.dto.TimeResponse;
import roomescape.domain.repository.TimeRepository;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeQueryController {
    private final TimeRepository jdbcTimeRepository;

    public TimeQueryController(final TimeRepository jdbcTimeRepository) {
        this.jdbcTimeRepository = jdbcTimeRepository;
    }

    @GetMapping
    public List<TimeResponse> findTimes() {
        return TimeResponse.from(jdbcTimeRepository.findAll());
    }
}
