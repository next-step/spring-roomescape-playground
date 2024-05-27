package roomescape.repository;

import roomescape.domain.TimeDomain;

import java.util.List;

public interface TimeRepository {
    List<TimeDomain> findAll();
    TimeDomain save(TimeDomain timeDomain);
    void deleteById(Long id);

}
