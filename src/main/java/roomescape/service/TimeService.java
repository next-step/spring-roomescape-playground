package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.RequestTime;
import roomescape.exception.BadRequestException;
import roomescape.model.Time;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Transactional(readOnly = true)
    public List<Time> getTimeList() {
        return timeRepository.findAll();
    }

    @Transactional
    public Time addTime(RequestTime requestTime) {
        Time time = new Time(requestTime.time());

        return timeRepository.save(time);
    }

    @Transactional
    public void cancelTime(Long id) {
        if (timeRepository.findById(id) == null) {
            throw new BadRequestException("존재하지 않는 아이디입니다.");
        }

        timeRepository.delete(id);
    }
}
