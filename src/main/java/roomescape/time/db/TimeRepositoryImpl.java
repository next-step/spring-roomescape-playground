package roomescape.time.db;


import java.sql.Time;
import java.util.List;

public interface TimeRepositoryImpl {

    List<TimeEntity> findAll();

    TimeEntity findById(Long id);

    TimeEntity save(TimeEntity timeEntity);

    void deleteById(Long id);
}
