package roomescape.service;

import roomescape.dao.TimeRepository;
import roomescape.domain.Time;

import java.util.List;

public class TimeService {

    private TimeRepository timeRepository;

    public List<Time> getAllReservation() {
        return timeRepository.findAll();
    }

    public Time inserTime(Time time) {
        return timeRepository.save(time);
    }

    public int deleteTime(int id) {
        return timeRepository.delete(id);
    }
}
