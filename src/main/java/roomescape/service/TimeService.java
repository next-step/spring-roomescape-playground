package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;
import roomescape.dto.TimeResponseDto;
import roomescape.dto.TimeRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {

    private final TimeDAO timeDAO;

    @Autowired
    public TimeService(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public List<TimeResponseDto> getAllTimes() {
        List<Time> times = timeDAO.findAllTime();
        return times.stream()
                .map(time -> new TimeResponseDto(time.getId(), time.getTime()))
                .collect(Collectors.toList());
    }
    public TimeResponseDto createTime(TimeRequestDto timeRequestDto) {
        Time time = new Time(null, timeRequestDto.getTime());
        Time newTime = timeDAO.insert(time);
        return new TimeResponseDto(newTime.getId(), newTime.getTime());
    }

    public void deleteTime(Long id) {
        timeDAO.delete(id);
    }
}
