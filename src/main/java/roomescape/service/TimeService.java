package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.model.dto.TimeDto;
import roomescape.model.entity.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> findTimes() {
        return this.timeRepository.findAll();
    }

    public Time join(TimeDto timeDto) {
        return this.timeRepository.save(timeDto.toEntity());
    }

    public int remove(Long id) {
        return this.timeRepository.delete(id);
    }
}
