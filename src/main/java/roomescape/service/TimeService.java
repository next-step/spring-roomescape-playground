package roomescape.service;


import org.springframework.stereotype.Service;
import roomescape.controller.dto.TimeRequestDto;
import roomescape.controller.dto.TimeResponseDto;
import roomescape.exception.NotFoundException;
import roomescape.model.Time;
import roomescape.repository.TimeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public TimeResponseDto create(TimeRequestDto timeRequestDto) {
        Time time = timeRequestDto.toEntity();
        return new TimeResponseDto(timeRepository.save(time));
    }

    public List<TimeResponseDto> read() {
        List<TimeResponseDto> timesDto = new ArrayList<>();
        List<Time> times = timeRepository.findAllTimes();
        for (Time time : times) {
            timesDto.add(new TimeResponseDto(time));
        }
        return timesDto;
    }

    public void delete(Long id) {
        int affectedRow = timeRepository.delete(id);
        if (affectedRow == 0) {
            throw new NotFoundException("삭제할 시간이 없습니다.");
        }
    }
}
