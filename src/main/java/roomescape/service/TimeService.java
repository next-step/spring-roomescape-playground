package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;
import roomescape.dto.TimeDto;
import roomescape.dto.TimeResDto;

@Service
public class TimeService {
    private final TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @Transactional
    public TimeResDto addTime(TimeDto timeDto) {
        Time time = new Time(0, timeDto.time());
        timeDAO.insert(time);
        int id = timeDAO.getId(time)
                .orElseThrow(() -> new IllegalArgumentException("Time ID를 찾을 수 없습니다."));
        return new TimeResDto(id, time.getTime());
    }

    @Transactional(readOnly = true)
    public List<TimeResDto> getAllTimes() {
        List<Time> times = timeDAO.findAll();
        return times.stream()
                .map(TimeResDto::from)
                .toList();
    }

    @Transactional
    public void deleteTime(int id) {
        timeDAO.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TimeResDto findByTime(String time) {
        Time timeResult = timeDAO.findByTime(time)
                .orElseThrow(() -> new IllegalArgumentException("해당 시간이 존재하지 않습니다."));
        return TimeResDto.from(timeResult);
    }
}
