package roomescape.time.service;

import org.springframework.stereotype.Service;
import roomescape.time.dao.TimeDao;
import roomescape.time.domain.Time;

import java.util.List;

@Service
public class TimeService {
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<Time> findAll() {
        return timeDao.findAll();
    }

    public Time save(Time time) {
        return timeDao.save(time);
    }

    public void deleteById(Long id) {
        timeDao.deleteById(id);
    }
}
