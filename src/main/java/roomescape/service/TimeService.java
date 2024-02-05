package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeQueryingDAO;
import roomescape.dao.TimeUpdatingDAO;
import roomescape.domain.Time;
import roomescape.dto.TimeAddRequest;

@Service
public class TimeService {

    private final TimeQueryingDAO timeQueryingDAO;

    private final TimeUpdatingDAO timeUpdatingDAO;

    @Autowired
    public TimeService(TimeQueryingDAO timeQueryingDAO, TimeUpdatingDAO timeUpdatingDAO){
        this.timeQueryingDAO = timeQueryingDAO;
        this.timeUpdatingDAO = timeUpdatingDAO;
    }


    public Time save(TimeAddRequest time) {
        Time newTime = new Time(time.getTime());
        Number timeId = timeUpdatingDAO.save(newTime);

        return newTime.toEntity(timeId.longValue(), time.getTime());
    }

    public List<Time> findAllTimes() {
        return timeQueryingDAO.getAllTimes();
    }

    public int delete(long id) {
        return timeUpdatingDAO.delete(id);
    }
}
