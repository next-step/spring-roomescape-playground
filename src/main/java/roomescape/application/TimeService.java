package roomescape.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.dto.CreateInfoTimeDto;
import roomescape.application.dto.CreateTimeDto;
import roomescape.application.dto.ReadTimeDto;
import roomescape.application.exception.TimeNotFoundException;
import roomescape.domain.Time;
import roomescape.domain.repository.TimeRepository;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<ReadTimeDto> readAll() {
        final List<Time> times = timeRepository.findAll();

        return times.stream()
                    .map(ReadTimeDto::from)
                    .toList();
    }

    public void deleteById(final Long id) {
        if (!timeRepository.existsById(id)) {
            throw new TimeNotFoundException("존재하지 않는 시간입니다.");
        }

        timeRepository.deleteById(id);
    }
}
