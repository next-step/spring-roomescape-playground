package roomescape.time.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.common.exception.NoSuchElementToDeleteException;
import roomescape.time.dto.TimeCreationRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.model.Time;
import roomescape.time.model.TimeManager;
import roomescape.time.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponse> getTimeList() {
        List<Time> timeList = timeRepository.findAllTime();

        return timeList.stream().map(TimeResponse::from).toList();
    }

    public TimeResponse addTime(TimeCreationRequest request) {
        List<Time> timeList = timeRepository.findAllTime();
        TimeManager timeManager = new TimeManager(timeList);

        Time newTime = TimeCreationRequest.toEntityFrom(request);

        timeManager.validateTime(newTime);
        Long id = timeRepository.insertWithKeyHolder(newTime);
        newTime.setId(id);

        return TimeResponse.from(newTime);
    }

    public void deleteTime(Long id) {
        Time time = timeRepository.findTimeById(id)
                .orElseThrow(() -> new NoSuchElementToDeleteException("There is no reservation with id " + id));
        timeRepository.delete(time);
    }
}