package roomescape.timeManage.repository;

import org.springframework.stereotype.Repository;
import roomescape.timeManage.Time;
import roomescape.timeManage.TimeDao;

import java.util.List;

@Repository
public class TimeRepository {
    private final TimeDao timeDao;

    public TimeRepository(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<Time> findAll() {
        return timeDao.findAll();
    }

    public Time findById(Long id) {
        return timeDao.findById(id);
    }

    public Time save(String time) {
        return timeDao.insert(time);
    }

    public void delete(Long id) {
        timeDao.deleteById(id);
    }
}
