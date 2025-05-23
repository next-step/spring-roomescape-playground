package roomescape.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dao.TimeDAO;
import roomescape.exception.NotFoundReservationException;

@Service
public class TimeService {

    private final TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public Time add(Time time) {
        return timeDAO.save(time);
    }

    public Time findById(Long id) {
        return timeDAO.findById(id).orElseThrow(() ->
                new NotFoundReservationException("해당 시간 ID가 존재하지 않습니다."));
    }

    public List<Time> findAll() {
        return timeDAO.findAll();
    }

    public void delete(Long id) {
        timeDAO.deleteById(id);
    }
}
