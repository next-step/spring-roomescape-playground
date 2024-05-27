package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DAO.TimeDAO;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {
    private final TimeDAO timeDAO;

    @Autowired
    public TimeService(DataSource dataSource) {
        this.timeDAO = new TimeDAO(dataSource);
    }

    public List<TimeResponse> findAll() {
        return timeDAO.findAll().stream()
                .map(TimeResponse::from)
                .collect(Collectors.toList());
    }

    public TimeResponse findById(long id) {
        Time time = timeDAO.findById(id);
        return TimeResponse.from(time);
    }

    public long save(TimeRequest request) {
        Time time = new Time(request.id(), request.time());
        return timeDAO.save(time);
    }

    public long deleteById(long id) {
        return timeDAO.deleteById(id);
    }
}