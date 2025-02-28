package roomescape.service;


import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.time.TimeJdbcDAO;
import roomescape.entity.Time;

@Service
public class TimeService {

    private final TimeJdbcDAO timeJdbcDAO;

    public TimeService(TimeJdbcDAO timeJdbcDAO) {
        this.timeJdbcDAO = timeJdbcDAO;
    }

    public Time createTime(Time time) {
        return timeJdbcDAO.create(time);
    }

    public List<Time> getAllTimes() {
        return timeJdbcDAO.getAll();
    }

    public Time getTimeById(long id) {
        return timeJdbcDAO.getById(id);
    }

    public void deleteTime(long id) {
        timeJdbcDAO.delete(id);
    }
}
