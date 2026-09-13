package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Time createTime(TimeRequest timeRequest) {

        timeRequest.validate();
        Time time = timeRepository.createTime(timeRequest);

        return time;
    }

    public List<Time> readTimes() {
        return timeRepository.readTimes();
    }

    public void deleteTime(long id) {
        timeRepository.deleteTime(id);
    }
}
