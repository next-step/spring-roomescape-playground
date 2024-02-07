package roomescape.service;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.NotFoundTimeException;

@Service
public class TimeService {
    private final TimeDAO timeDAO;

    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public TimeResponse insertNewTime(TimeRequest timeRequest) {

        Time time = new Time(timeRequest.getTime());
        Long id = timeDAO.insertNewTime(time);
        time = Time.toEntity(time, id);
        return new TimeResponse(time);
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
