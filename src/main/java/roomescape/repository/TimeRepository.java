package roomescape.repository;

import java.util.List;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;

public interface TimeRepository {

    List<Time> findAll();

    Time findById(Long id);

    Time create(TimeRequest timeRequest);

    int delete(Long id);
}
