package roomescape.domain.time.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.time.dto.request.TimeCreateRequestDto;
import roomescape.domain.time.entity.Time;
import roomescape.domain.time.repository.TimeDao;
import roomescape.exception.custom.BusinessException;

import java.util.List;

import static roomescape.exception.ErrorCode.TIME_NOT_FOUND;

@Service
public class TimeService {
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public Time saveTime(TimeCreateRequestDto requestDto) {
        Long timeId = timeDao.insert(requestDto);
        return requestDto.toEntity(timeId);
    }

    public List<Time> getTimes() {
        return timeDao.findAllTimes();
    }

    public void deleteTime(Long timeId) {
        Time time = timeDao.findTimeById(timeId)
                .orElseThrow(() -> new BusinessException(TIME_NOT_FOUND));

        timeDao.deleteTimeById(timeId);
    }
}