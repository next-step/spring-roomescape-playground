package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.entity.Time;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

  TimeRepository timeRepository;

  @Autowired
  TimeService(TimeRepository timeRepository) {
    this.timeRepository = timeRepository;
  }


  public List<Time> searchAllTimes() {
    return timeRepository.findAll();
  }

  public Time setTime(Time time) {
    return timeRepository.create(time.getTime());
  }

  public void removeTime(Long timeId) {
    timeRepository.deleteById(timeId);
  }
}
