package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.domain.Time.NotFoundTimeException;
import roomescape.dto.TimeRequestDto;

@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {

  private final TimeDao timeDao;

  @Override
  public List<Time> getTimes() {
    return timeDao.findAll();
  }

  @Override
  public Time addTime(final TimeRequestDto timeRequestDto) {
    Time time = timeDao.save(timeRequestDto);
    return time;
  }

  @Override
  public void removeTime(final Long id) {
    if (timeDao.delete(id) == 0)
      throw new NotFoundTimeException();
  }
}
