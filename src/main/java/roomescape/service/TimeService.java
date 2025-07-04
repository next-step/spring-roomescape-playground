package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.RequestTime;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.global.exception.NotFoundException;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(final TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @Transactional
    public Time create(final RequestTime requestTime) {
        return timeDao.save(requestTime.getTime());
    }

    @Transactional(readOnly = true)
    public List<Time> findAll() {
        return timeDao.findAll();
    }

    private Time findById(final Long id) {
        return timeDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 시간이에요."));
    }

    @Transactional
    public void deleteById(final Long id) {
        Time time = findById(id);
        timeDao.deleteById(time.id());
    }
}
