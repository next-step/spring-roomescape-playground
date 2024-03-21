package roomescape.domain.time.service;

import org.springframework.stereotype.Service;
import roomescape.domain.time.dto.CreateTimeRequestDto;
import roomescape.domain.time.dto.TimeDto;
import roomescape.domain.time.repository.TimeDao;

import java.util.List;

@Service
public class TimeService {
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public TimeDto saveTime(CreateTimeRequestDto requestDto) {
        return TimeDto.toDto(timeDao.save(requestDto));
    }

    public List<TimeDto> getTimes() {
        return timeDao.findAllTimes()
                .stream()
                .map(TimeDto::toDto)
                .toList();
    }

    public void removeTime(Long timeId) {
        timeDao.delete(timeId);
    }
}
