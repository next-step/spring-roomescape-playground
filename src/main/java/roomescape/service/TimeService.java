package roomescape.service;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;
import roomescape.exception.NoParameterException;
import roomescape.exception.NotFoundTimeException;

@Service
public class TimeService {
    private final TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public Time insertNewTime(Time time) {
        if(Time.checkValidity(time)) throw new NoParameterException();

        Long id = timeDAO.insertNewTime(time);
        return Time.toEntity(time, id);
    }

    public List<Time> findAllTimes() { return timeDAO.findAllTimes(); }

    public void deleteTime(Long id) {
        Time time = timeDAO.findAllTimes().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundTimeException::new);

        timeDAO.deleteTime(id);
    }
}
