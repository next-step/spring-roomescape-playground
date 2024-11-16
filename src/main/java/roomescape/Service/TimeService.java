package roomescape.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.DAO.TimeDAO;
import roomescape.Domain.Time;

@Component
public class TimeService {

  private final TimeDAO timeDAO;

  public TimeService(TimeDAO timeDAO) {
    this.timeDAO = timeDAO;
  }

  public List<Time> getAllTimes() {
    return timeDAO.findAllTimes();
  }

  public Time getTimeByID(Long id) {
    return timeDAO.findTimeByID(id);
  }

  public List<Time> getTimesByID(Long id) {
    return timeDAO.findTimesByID(id);
  }

  public Long addTimeByKey(Time time) {
    return timeDAO.insertWithKeyHolder(time);
  }

  public void deleteTimeByID(Long id) {
    timeDAO.delete(id);
  }
}
