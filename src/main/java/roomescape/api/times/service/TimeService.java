package roomescape.api.times.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.api.times.dto.response.TimeResponse;
import roomescape.db.time.dao.TimeDao;
import roomescape.db.time.entity.TimeEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeDao timeDao;

    @Transactional(readOnly = true)
    public List<TimeResponse> getTimes() {
        return timeDao.getTimes().stream()
                .map(timeEntity -> TimeResponse.of(timeEntity.getId(), timeEntity.getTime()))
                .toList();
    }

    @Transactional
    public TimeResponse createTime(final String time) {
        final TimeEntity timeEntity = timeDao.createTime(time);
        return TimeResponse.of(timeEntity.getId(), timeEntity.getTime());
    }

    @Transactional
    public void deleteTime(final long timeId) {
        timeDao.deleteTime(timeId);
    }
}
