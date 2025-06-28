package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@Service
public class TimeService {
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<TimeResponse> getAllTimes() {
        return timeDao.findAll().stream()
            .map(TimeResponse::from)
            .toList();
    }

    public TimeResponse createTime(TimeRequest request) {
        Time time = new Time(null, request.time());
        Time saved = timeDao.save(time);
        return TimeResponse.from(saved);
    }

    public void deleteTime(long id) {
        timeDao.deleteById(id);
    }
}
