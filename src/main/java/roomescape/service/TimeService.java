package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> getTimes() {
        return timeRepository.getTimes();
    }

    public Time createTime(LocalTime time) {
        return timeRepository.saveTime(time);
    }

    public Time getTime(long id) {
        return timeRepository.getTime(id)
                             .orElseThrow(NoSuchElementException::new);
    }

    public void deleteTime(long id) {

        int deletedCount = timeRepository.deleteTime(id);

        if (deletedCount == 0) {
            throw new NoSuchElementException();
        }
    }
}
