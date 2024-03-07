package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roomescape.controller.dto.TimeCreate;
import roomescape.controller.dto.TimeResponse;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;

@Service
@Transactional
public class TimeServiceImpl implements TimeService {

    private final TimeDao timeDao;

    public TimeServiceImpl(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeResponse> findAll() {
        return timeDao.findAll().stream()
            .map(TimeResponse::from)
            .toList();
    }

    @Override
    public TimeResponse add(TimeCreate request) {
        Time time = timeDao.save(request.toTime());
        return TimeResponse.from(time);
    }

    @Override
    public void remove(Long id) throws NoSuchElementException {
        if (!timeDao.existsById(id)) {
            throw new NoSuchElementException("존재하지 않는 예약 시간입니다.");
        }

        timeDao.deleteById(id);
    }
}
