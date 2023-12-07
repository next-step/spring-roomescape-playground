package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.CreateReservationTime;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;

@Service
@Transactional(readOnly = true)
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        return timeDao.findAll().stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    @Transactional
    public Time create(CreateReservationTime request) {
        Time time = request.toReservationTime();
        return timeDao.save(time);
    }

    @Transactional
    public void remove(Long id) {
        if (!timeDao.existsById(id)) {
            throw new IllegalArgumentException("예약 시간이 존재하지 않습니다. id: " + id);
        }
        timeDao.deleteById(id);
    }
}
