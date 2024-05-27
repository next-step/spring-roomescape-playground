package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Times;
import roomescape.dto.SaveTimeRequest;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {
    private TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Times saveTime(SaveTimeRequest request) {
        return timeRepository.save(request.toTimes());
    }

    public Times findById(Long id) {
        Times time = timeRepository.findById(id);
        if(time == null){
            throw new NotFoundTimeException("시간이 존재하지 않습니다.");
        }
        return time;
    }
}
