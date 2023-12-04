package roomescape.domain.time.service;

import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.time.dao.TimeRepository;
import roomescape.domain.time.entity.Time;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    @Transactional
    public Time createTime(String time) {
        return timeRepository.save(Time.builder()
                .time(LocalTime.parse(time))
                .build());
    }

    public List<Time> getTimes() {
        return timeRepository.findAll();
    }

    @Transactional
    public void deleteTime(long timeId) {
        timeRepository.deleteById(timeId);
    }
}
