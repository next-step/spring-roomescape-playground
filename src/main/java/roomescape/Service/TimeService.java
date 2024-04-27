package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DAO.ReservationReposiory;
import roomescape.DAO.TimeRepository;
import roomescape.Domain.Time;

import java.util.List;

@Service
public class TimeService {
  @Autowired
  private TimeRepository timeRepository;

  public List<Time> getAllTimes() {
    return timeRepository.getAllTimes();
  }

  public Time newTime(Time time) {
    return timeRepository.makeNewTime(time);
  }

  public void deleteTimeById(Long id) {
    timeRepository.deleteTimeById(id);
  }
}
