package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;
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

    public List<TimeRequestDto> findAllTimes() {
        return timeRepository.findAllTimes().stream()
                .map(TimeRequestDto::convertToDto)
                .collect(Collectors.toList());
    }

    public Time createTime(TimeRequestDto timeDTO) {
        Long newId = timeRepository.insertTimeId(timeDTO);
        return timeDTO.toEntity(newId);
    }

    public void deleteTime(Long id) {
        Time time = timeRepository.findTimeById(id)
                .orElseThrow(() -> new NotFoundTimeException("Time with id " + id + " not found"));
        timeRepository.delete(id);
    }
}