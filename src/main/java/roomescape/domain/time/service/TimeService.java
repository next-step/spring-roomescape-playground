package roomescape.domain.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.time.dto.response.GetTimesResponse;
import roomescape.domain.time.entity.Time;
import roomescape.domain.time.repository.TimesRepositoryJdbc;
import roomescape.global.BusinessException;

import java.time.LocalTime;
import java.util.List;

import static roomescape.global.ErrorCode.TIME_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TimeService {

    private final TimesRepositoryJdbc timeRepositoryJdbc;

    @Transactional
    public Time saveTime(final LocalTime time) {
        return timeRepositoryJdbc.save(time);
    }

    public List<GetTimesResponse> findAllTimes() {
        return timeRepositoryJdbc.findAll()
                .stream()
                .map(GetTimesResponse::from)
                .toList();
    }

    @Transactional
    public void deleteTimes(final long timesId) {
        timeRepositoryJdbc.deleteById(timesId);
    }


    public long findTimes(final long timesId) {
        return timeRepositoryJdbc.findById(timesId)
                .orElseThrow(() -> new BusinessException(timesId, "timesId", TIME_NOT_FOUND))
                .getId();
    }
}
