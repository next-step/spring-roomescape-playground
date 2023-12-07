package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.Repository.TimeRepository;
import roomescape.domain.Time;
import roomescape.dto.TimeCreateForm;
import roomescape.dto.TimeResponseForm;


import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponseForm> getAllTimes() {
        try {
            List<Time> times = timeRepository.findAll();
            return times.stream()
                    .map(TimeResponseForm::new)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("시간을 가져올 수 없습니다", e);
        }
    }

    public TimeResponseForm createTime(TimeCreateForm form) {
        try {
            Time newTime = form.toEntity();
            Long newId = timeRepository.save(newTime);
            Time time = timeRepository.findById(newId);

            return new TimeResponseForm(time);
        } catch (Exception e) {
            throw new RuntimeException("시간을 생성할 수 없습니다", e);
        }
    }

    public void deleteTime(Long id) {
        timeRepository.deleteById(id);
    }
}

