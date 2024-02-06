package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.ReadTimeDto;
import roomescape.dto.CreateTimeRequestDto;
import roomescape.dto.CreateTimeResponseDto;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public void deleteTime(Long id) {
        Time time = timeRepository.findTimeById(id)
                .orElseThrow(() -> new NotFoundTimeException("Time with id " + id + " not found"));
        timeRepository.delete(id);
    }

    public CreateTimeResponseDto createTime(CreateTimeRequestDto timeDTO) {
        Time newTime = new Time(null, timeDTO.getTime());
        Time savedTime = timeRepository.saveTime(newTime);
        return convertToCreateTimeResponseDto(savedTime);
    }

    public List<ReadTimeDto> findAllTimes() {
        return timeRepository.findAllTimes().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CreateTimeResponseDto convertToCreateTimeResponseDto(Time time) {
        return new CreateTimeResponseDto(time.getId(), time.getTime());
    }

    private ReadTimeDto convertToDto(Time time) {
        return new ReadTimeDto(time.getId(), time.getTime());
    }

}