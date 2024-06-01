package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import roomescape.domain.Time;
import roomescape.exception.TimeNotFoundException;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    @Autowired
    private TimeRepository timeRepository;

    public List<Time> getAllTimes() {
        return timeRepository.findAll();
    }

    public Time getTimeById(long id) {
        if (!timeRepository.existsById(id)) {
            throw new TimeNotFoundException("Time with id " + id + " not found");
        }
        return timeRepository.getReferenceById(id);
    }

    public Time createTime(Time time) {
        return timeRepository.save(time);
    }

    public void deleteTimeById(long id) {
        if (!timeRepository.existsById(id)) {
            throw new TimeNotFoundException("Time with id " + id + " not found");
        }
        Time time = getTimeById(id);
        timeRepository.delete(time);
    }
}
