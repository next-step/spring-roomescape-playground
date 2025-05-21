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

    @PostConstruct
    public void init() {
        if (timeDAO.findAll().isEmpty()) {
            timeDAO.save(new Time(null, "10:00"));
            timeDAO.save(new Time(null, "13:00"));
            timeDAO.save(new Time(null, "17:00"));
        }
    }

    public Time add(String time) {
        return timeDAO.save(new Time(null, time));
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
