package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeQueryingDAO;
import roomescape.dao.TimeUpdatingDAO;
import roomescape.domain.Time;

@Service
public class TimeService {
    TimeQueryingDAO timeQueryingDAO;

    TimeUpdatingDAO timeUpdatingDAO;

    public TimeService(TimeQueryingDAO timeQueryingDAO, TimeUpdatingDAO timeUpdatingDAO){
        this.timeQueryingDAO = timeQueryingDAO;
        this.timeUpdatingDAO = timeUpdatingDAO;
    }


    public Time save(Time time) {
        Time newTime = new Time(time.getTime());
        Number timeId = timeUpdatingDAO.save(newTime);
        newTime.setId(timeId.longValue());

        return newTime;
    }

    public List<Time> findAllTimes() {
        return timeQueryingDAO.getAllTimes();
    }

    public int delete(long id) {
        return timeUpdatingDAO.delete(id);
    }
}
