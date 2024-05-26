package roomescape.time.db;

import roomescape.time.model.TimeRequest;

import java.util.List;

public interface TimeRepository {

    List<TimeEntity> findAllList();

    int countTime();

    TimeEntity findTimeById(Long id);

    TimeEntity insertTime(TimeRequest timeRequest);

    void deleteTime(Long id);
}
