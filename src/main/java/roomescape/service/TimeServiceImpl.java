package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.handler.ErrorStatus;
import roomescape.handler.exception.TimeException;

@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {

  private final TimeDao timeDao;

  @Override
  public Time create(Time time) {

    return timeDao.save(time);
  }

  @Override
  public Time findById(Long id) {

    return timeDao.findById(id)
        .orElseThrow(() -> new TimeException(ErrorStatus._NOT_FOUND_TIME));

  }

  @Override
  public Time delete(Long id) {

    Time time = this.findById(id);
    timeDao.remove(time);

    return time;
  }

  @Override
  public List<Time> findAll() {
    return timeDao.findAll();
  }
}

