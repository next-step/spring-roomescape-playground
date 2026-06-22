package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.util.List;

@Repository
public class TimeRepository {

    private final TimeDao timeDao;

    public TimeRepository(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<ReservationTime> findAll() {
        return timeDao.findAll();
    }

    public ReservationTime save(ReservationTime reservationTime) {
        return timeDao.save(reservationTime);
    }

    public boolean deleteById(Long id) {
        return timeDao.deleteById(id);
    }
}
