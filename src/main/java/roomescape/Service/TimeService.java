package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.domain.TimeDao;

import java.util.List;

@Service
public class TimeService {
    private final TimeDao timeDao;
    public TimeService(TimeDao timeDao){
        this.timeDao = timeDao;
    }
    public List<Time> getAllTimes(){
        return timeDao.getAllTime();
    }
    public Time saveTime(Time time){
        int id = timeDao.save(time);
        time.setId(id);
        return time;
    }
    public void deleteTime(int id){
        timeDao.deleteTimeById(id);
    }
}
