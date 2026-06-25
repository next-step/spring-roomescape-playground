package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            throw new IllegalArgumentException("시간은 hh:mm 형식이여야 합니다.");
        }
    }

    private void validateRange(Integer hour, Integer minutes) {
        boolean isInRange = 0 <= hour && hour < 24 && 0 <= minutes && minutes < 60;
        if (!isInRange) {
            throw new IllegalArgumentException("시는 00~23, 분은 00~60 사이의 숫자로 표현해야 합니다.");
        }
    }

    public void deleteTimeById(Long id) {
        timeRepository.deleteTimeById(id);
    }
}
