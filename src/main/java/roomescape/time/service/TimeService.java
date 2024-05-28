package roomescape.time.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequestDto;
import roomescape.time.dto.TimeResponseDto;
import roomescape.time.repository.TimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(@Qualifier("JdbcTimeRepository") TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponseDto> getTimes() {
        return timeRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    public TimeResponseDto createTime(TimeRequestDto requestDto) {
        Time time = new Time(0L, requestDto.time());
        Time savedTime = timeRepository.save(time);
        return toResponseDto(savedTime);
    }

    public void deleteTime(long timeId) {
        timeRepository.deleteById(timeId);
    }

    private TimeResponseDto toResponseDto(Time time) {
        return new TimeResponseDto(time.getId(), time.getTime());
    }

}
