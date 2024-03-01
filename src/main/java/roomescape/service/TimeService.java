package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository){
        this.timeRepository = timeRepository;
    }

    public Long saveTime(Time newTime){
        return timeRepository.saveTime(newTime);
    }

    public List<Time> findAllTime(){
        return timeRepository.findAllTime();
    }

    public void deleteTime(Long id){
        //timeRepository.findById(id);
        timeRepository.deleteTime(id);
    }
}
