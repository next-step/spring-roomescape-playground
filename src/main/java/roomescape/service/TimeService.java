package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.TimeDto;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<TimeDto> getAllTimes() {
        return timeDao.getAllTimes().stream()
            .map(this::convertToDto)
            .toList();
    }

    public TimeDto addTime(TimeDto request) {
        Time response = convertToDomain(request);
        timeDao.saveTime(response);
        return convertToDto(response);
    }

    public void deleteTime(Long request) {
        timeDao.removeTime(request);
    }

    private Time convertToDomain(TimeDto dto) {
        return new Time(dto.id(), dto.time());
    }

    private TimeDto convertToDto(Time domain) {
        return new TimeDto(domain.getId(), domain.getTime());
    }
}
