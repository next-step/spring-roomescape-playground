package roomescape.time.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.exception.InvalidReservationRequestException;
import roomescape.time.dao.TimeDao;
import roomescape.time.exception.TimeNotFoundException;
import roomescape.time.model.Time;
import roomescape.time.response.TimeResponse;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<TimeResponse> getTimes() {
        return timeDao.findAll().stream()
            .map(TimeResponse::new)
            .toList();
    }

    public Time getTime(Long id) {
        return timeDao.findById(id)
            .orElseThrow(() -> new InvalidReservationRequestException("Invalid time ID: " + id));
    }

    public TimeResponse create(LocalTime time) {
        Time savedTime = timeDao.insert(time);
        return TimeResponse.of(savedTime);
    }

    public void delete(Long id) {
        if (!timeDao.delete(id)) {
            throw new TimeNotFoundException("Time not found with id: " + id);
        }
    }
}
