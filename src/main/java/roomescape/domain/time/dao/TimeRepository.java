package roomescape.domain.time.dao;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.entity.Time;

@Repository
public interface TimeRepository {

    Time save(Time time);

    Time findById(long timeId);

    Time findByTime(LocalTime time);

    void delete(Time time);

    void deleteById(long timeId);

    List<Time> findAll();
}
