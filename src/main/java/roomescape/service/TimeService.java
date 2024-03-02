package roomescape.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.web.dao.TimeDao;
import roomescape.web.dto.ReservationDto;
import roomescape.web.dto.TimeDto;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TimeService {
    private final TimeDao timeDao;

    private final AtomicLong index = new AtomicLong(0);

    @Autowired
    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @Transactional
    public List<TimeDto> getAllTime() {
        List<Time> time = timeDao.getAllTimes();
        return time.stream()
                .map(TimeDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TimeDto createTime(String time) {

        Long newId = index.incrementAndGet();
        Time times = timeDao.createTime(newId,time);
        return new TimeDto(times);
    }

    @Transactional
    public void deleteTimeById(Long id) {
        timeDao.deleteTimeById(id);
    }

    @Transactional
    public Optional<Time> getTimeById(Long id) {
        return timeDao.getTimeById(id);

    }
}
