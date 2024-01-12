package roomescape.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.time.dao.TimeQueryRepository;
import roomescape.time.dao.TimeUpdateRepository;
import roomescape.time.domain.Time;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeService {

    private final TimeQueryRepository timeQueryRepository;
    private final TimeUpdateRepository timeUpdateRepository;

    public List<Time> getAllTimes() {
        return timeQueryRepository.getAllTimes();
    }

    public Time createTime(LocalTime time) {
        if (time == null ) {
            throw new NotFoundTimeException("빈 값이 존재합니다!");
        }
        return timeUpdateRepository.insert(time);
    }
    
    public void deleteTime(Long id) {
        timeUpdateRepository.delete(id);
    }

    public static class NotFoundTimeException extends RuntimeException {
        public NotFoundTimeException(String message) {
            super(message);
        }
    }
}
