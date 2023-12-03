package roomescape.Time;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Time postTimes(Time time) {
        return timeRepository.insertTime(time);
    }

    public List<Time> getTimes() {
        return timeRepository.SelectAll();
    }

    public void deleteTimes(Long id) {
        timeRepository.DeleteById(id);
    }
}
