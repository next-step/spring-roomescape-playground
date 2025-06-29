package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
import roomescape.repository.TimeRepository;
import roomescape.model.Time;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Time addTime(String timeValue) {
        Time time = new Time(null, timeValue);
        verification(time);
        return timeRepository.save(time);
    }

    public List<Time> getTime() {
        return timeRepository.findAll();
    }

    public void deleteTime(int id) {
        boolean delete = timeRepository.delete(id);
        if (!delete) {
            throw new BadRequestException("해당 아이디는 존재하지 않습니다");
        }
    }

    public Time findById(int id) {
        Time time = timeRepository.findById(id);
        if (time == null) {
            throw new BadRequestException("해당 아이디에는 시간이 존재하지 않습니다");
        }
        return time;
    }

    private void verification(Time time) {
        if (time == null) {
            throw new BadRequestException("해당 시간은 값이 없거나 입력되지 않았습니다");
        }
    }


}
