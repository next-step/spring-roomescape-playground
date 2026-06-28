package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeRepository {

    private final TimeDao timeDao;

    public TimeRepository(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<Time> findAll() {
        return timeDao.findAll();
    }

    public Optional<Time> findById(Long id) {
        return timeDao.findById(id);
    }

    public boolean existsByTime(LocalTime time) {
        return timeDao.existsByTime(time);
    }

    public Time save(Time reservationTime) {
        return timeDao.save(reservationTime);
    }

    public boolean deleteById(Long id) {
        return timeDao.deleteById(id);
    }
}
