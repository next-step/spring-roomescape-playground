package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dao.TimeDAO;

@Service
public class TimeService {

    private final TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public Time add(String time) {
        return timeDAO.save(new Time(null, time));
    }

    public List<Time> findAll() {
        return timeDAO.findAll();
    }

    public void delete(Long id) {
        timeDAO.deleteById(id);
    }
}
