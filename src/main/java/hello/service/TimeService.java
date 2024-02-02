package hello.service;

import hello.domain.Time;
import hello.exceptions.NotFoundTimeException;
import hello.repository.TimeRepository;
import hello.controller.dto.CreateTimeDto;
import hello.service.dto.TimeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeDto> getTimeList() {
        return timeRepository.findAllTimes()
                .stream()
                .map(TimeDto::from)
                .toList();
    }

    public TimeDto save(CreateTimeDto dto) {
        Time savedTime = timeRepository.save(new Time(null, dto.getTime()));
        return TimeDto.from(savedTime);
    }

    public void deleteById(Long id) {
        int count = timeRepository.delete(id);
        if (count==0) throw new NotFoundTimeException();
    }
}
