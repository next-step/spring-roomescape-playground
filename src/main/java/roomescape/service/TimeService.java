package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.TimeRequest;
import roomescape.exception.NotFoundException;
import roomescape.model.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> findTimes() {
        return timeRepository.findAll();
    }

    public Time createTime(TimeRequest timeRequest) {
        Time time =
                new Time(
                        null,
                        timeRequest.time()
                );

        return timeRepository.save(time);
    }

    public void deleteTime(Long id) {
        int affectedRows = timeRepository.delete(id);

        if (affectedRows == 0) {
            throw new NotFoundException("예약 가능 시간을 찾을 수 없습니다.");
        }
    }
}
