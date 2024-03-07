package roomescape.service;

import roomescape.domain.Time;

import java.util.List;

public interface TimeService {
    List<Time> getAllTime();
    Time addTime(Time time);

    void deleteTime(Long id);

}
