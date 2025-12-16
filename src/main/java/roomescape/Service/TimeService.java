package roomescape.Service;

import org.springframework.stereotype.Service;
import roomescape.DAO.TimeDao;
import roomescape.Domain.Time;

import java.util.List;

@Service
public class TimeService {
    private final TimeDao timeDao;
    public TimeService(TimeDao timeDao){
        this.timeDao = timeDao;
    }

    // Read
    public List<Time> findAll(){
        return timeDao.findAll();
    }

    // Create
    public Long add(Time time){
        return timeDao.add(time);
    }

    // Delete
    public int deleteByid(Long id){
        return  timeDao.deleteByid(id);
    }
}
