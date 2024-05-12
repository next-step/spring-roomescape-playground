package roomescape.time.service;

import org.springframework.stereotype.Service;
import roomescape.time.Time;
import roomescape.time.repository.TimeDAO;

import java.util.List;

@Service
public class TimeService {

    private TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public List<Time> findTimes() {
        return timeDAO.findAll();
    }

    public Time createTime(Time time) {
        return timeDAO.save(time);
    }

    public void deleteTime(Long id) {
        timeDAO.deleteTime(id);
    }
}
