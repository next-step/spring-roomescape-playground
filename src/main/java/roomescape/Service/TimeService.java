package roomescape.Service;

import org.springframework.stereotype.Service;
import roomescape.repository.TimeRepository;
import roomescape.Domain.Time;
import roomescape.Dto.TimeCreateForm;
import roomescape.Dto.TimeResponseForm;

import java.util.List;


import java.util.stream.Collectors;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Long createTime(TimeCreateForm timeCreateForm) {
        return timeRepository.create(String.valueOf(timeCreateForm.getTime()));
    }

    public TimeResponseForm getTime(Long id) {
        Time time = timeRepository.findById(id);
        return TimeResponseForm.from(time);
    }

    public List<TimeResponseForm> getTimes() {
        List<Time> allTimes = timeRepository.findAll();
        return allTimes.stream()
                .map(TimeResponseForm::from)
                .collect(Collectors.toList());
    }

    public void deleteTime(Long id) {
        timeRepository.deleteTime(id);
    }
}



