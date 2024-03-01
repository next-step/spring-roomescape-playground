package roomescape.domain.time.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.domain.time.domain.Time;
import roomescape.domain.time.dao.TimeDAO;

@Service
public class TimeServiceImpl implements TimeService {

    private final TimeDAO timeRepository;

    public TimeServiceImpl(TimeDAO timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Override
    public Time createTime(Time time) {
        Long id = timeRepository.addTime(time);
        return timeRepository.getTimeById(id);
    }

    @Override
    public List<Time> getAllTime() {
        return timeRepository.getAllTime();
    }

    @Override
    public boolean deleteTime(Long id) {
        return timeRepository.deleteTime(id);
    }
}
