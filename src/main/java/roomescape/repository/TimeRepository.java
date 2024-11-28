package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.dao.TimeDao;
import roomescape.entity.Time;

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

    public Time save(Time time) {
        return timeDao.insert(time);
    }

    public void deleteById(Long id) {
        timeDao.delete(id);
    }
}
