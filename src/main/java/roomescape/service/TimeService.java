package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dto.TimeDto;
import roomescape.dataLayer.TimeRepository;
import roomescape.model.Time;
import java.util.List;

@Component
public class TimeService {
    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> getTimeList() {
        return timeRepository.getTimes();
    }

    public Time add(TimeDto timeDto) {
        Long newTimeId = timeRepository.add(timeDto);
        return timeRepository.getTimeById(newTimeId);
    }

    public void deleteTimeById(Long id) {
        timeRepository.deleteTimeById(id);
    }
}
