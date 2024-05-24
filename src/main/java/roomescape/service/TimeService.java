package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Times;
import roomescape.dto.SaveTimeRequest;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeRepository;

import java.util.Optional;


@Service
public class TimeService {
    private TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Times saveTime(SaveTimeRequest request) {
        Times time = timeRepository.save(request.toTimes());
        return time;
    }

    // Time을 변환해야 함.
    public Times findById(Long id) {
        Times time = timeRepository.findById(id);
        if(time == null){
            throw new NotFoundTimeException("시간이 존재하지 않습니다.");
        }
        return time;
    }
}
