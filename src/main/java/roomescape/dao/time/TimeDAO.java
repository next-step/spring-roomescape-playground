package roomescape.dao.time;

import java.util.List;
import roomescape.entity.Time;

public interface TimeDAO {

    Time create(Time time);

    List<Time> getAll();

    void delete(long id);

    Time getById(long id);
}
