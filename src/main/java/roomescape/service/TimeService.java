package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.common.ExceptionMessage;
import roomescape.dataLayer.TimeRepository;
import roomescape.dto.TimeDto;
import roomescape.model.Time;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> getTimes() {
        return timeRepository.getTimes();
    }

    public Time add(TimeDto timeDto) {
        validateTime(timeDto.time());
        Long newTimeId = timeRepository.add(timeDto.time());
        return timeRepository.getTimeById(newTimeId);
    }

    private void validateTime(String givenTime) {
        validateFormat(givenTime);
        List<Integer> timeNumbers = Arrays.stream(givenTime.split(":"))
                .map(Integer::parseInt)
                .toList();

        validateRange(timeNumbers.get(0), timeNumbers.get(1));
    }

    private void validateFormat(String givenTime) {
        if (!Pattern.matches("\\d{2}:\\d{2}", givenTime)) {
            throw new IllegalArgumentException(ExceptionMessage.BAD_TIME_FORMAT.getMessage());
        }
    }

    private void validateRange(Integer hour, Integer minutes) {
        boolean isInRange = 0 <= hour && hour < 24 && 0 <= minutes && minutes < 60;
        if (!isInRange) {
            throw new IllegalArgumentException(ExceptionMessage.BAD_TIME_RANGE.getMessage());
        }
    }

    public void deleteTimeById(Long id) {
        timeRepository.deleteTimeById(id);
    }
}
