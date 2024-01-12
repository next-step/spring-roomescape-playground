package roomescape.time;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeService {

  private final TimeRepositoryJdbc timeRepositoryJdbc;

  @Transactional
  public List<Time> getAllTimeInfo() {
    return timeRepositoryJdbc.findAll();
  }

  @Transactional
  public Time addTime(String time) {
    return timeRepositoryJdbc.save(time);
  }

  @Transactional
  public void deleteTime(Long id) {
    timeRepositoryJdbc.deleteById(id);
  }

}
