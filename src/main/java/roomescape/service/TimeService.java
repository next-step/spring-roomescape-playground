package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;
import roomescape.repository.TimeDao;

import java.time.LocalTime;
import java.util.List;

@Service
public class TimeService {
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<Time> findAllTimes() {
        return timeDao.findAllTimes();
    }

    public Time createTime(TimeRequestDto requestDto) {
        Time time = new Time(null, LocalTime.parse(requestDto.getTime()));
        Long id = timeDao.insert(time);

        return new Time(id, time.getTime());
    }

    public void deleteTime(Long id) {
        timeDao.deleteTime(id);
    }
}
