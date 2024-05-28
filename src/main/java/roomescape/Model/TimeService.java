package roomescape.Model;

import org.springframework.stereotype.Service;
import roomescape.Repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {

    private TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository){
        this.timeRepository=timeRepository;
    }

    public List<ReservationTime> getTimeList(){
        List<ReservationTime> list=timeRepository.getAllTimes();
        return list;
    }

    public ReservationTime saveTime(RequestTimeDTO requestTimeDTO){
        String time=requestTimeDTO.getTime();
        ReservationTime reservationTime=new ReservationTime(null,time);
        Long id=timeRepository.addTime(reservationTime);
        return timeRepository.getTimeById(id);
    }

    public ReservationTime viewTime(Long id){
        ReservationTime findTime=timeRepository.getTimeById(id);
        return findTime;
    }

    public void deleteTime(Long id){
        timeRepository.deleteTimeById(id);
    }
}
