package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.Domain.Time;
import roomescape.Repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    @Autowired
    public TimeRepository timeRepository;

    public List<Time> getAllTime() {
        return timeRepository.getAllTime();
    }

    public Time createdTime(Time time){
        return timeRepository.createTime(time);
    }

    public void deletedTime(Long id){
        timeRepository.deleteTimeById(id);
    }
}
