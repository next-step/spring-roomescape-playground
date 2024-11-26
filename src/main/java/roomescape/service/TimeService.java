package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.model.Time;

import java.util.List;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<Time> getAllTimes() {
        return timeDao.findAll();
    }

    public Time addTime(Time time) {
        if (time.getTime() == null || time.getTime().trim().isEmpty()) {
            throw new IllegalArgumentException("시간 값이 비어 있습니다.");
        }
        return timeDao.save(time);
    }

    public void deleteTime(Long id) {
        int rowsAffected = timeDao.deleteById(id);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("삭제할 시간이 없습니다.");
        }
    }
}
