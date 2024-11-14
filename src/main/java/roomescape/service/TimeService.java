package roomescape.service;

import java.util.List;
import roomescape.domain.Time;

public interface TimeService {

  Time findById(Long id);
  List<Time> findAll();
  Time create(final Time time);
  Time delete(final Long id);
}
