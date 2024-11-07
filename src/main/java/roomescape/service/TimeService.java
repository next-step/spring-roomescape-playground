package roomescape.service;

import java.util.List;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;

public interface TimeService {

  List<Time> getTimes();

  Time addTime(TimeRequestDto timeRequestDto);

  void removeTime(Long id);
}
