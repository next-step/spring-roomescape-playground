package roomescape.web.dao;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.List;
import java.util.Optional;

public interface TimeDao {

    List<Time> getAllTimes();

    Time createTime(Long Id, String time);

    void deleteTimeById(Long id);
}
