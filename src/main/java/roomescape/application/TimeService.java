package roomescape.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.dto.CreateInfoTimeDto;
import roomescape.application.dto.CreateTimeDto;
import roomescape.domain.Time;
import roomescape.domain.repository.TimeRepository;

@Service
@Transactional
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public CreateInfoTimeDto create(final CreateTimeDto createTimeDto) {
        final Time time = createTimeDto.toEntity();
        final Time persistTime = timeRepository.save(time);

        return CreateInfoTimeDto.from(persistTime);
    }
}
