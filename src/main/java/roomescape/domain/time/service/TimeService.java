package roomescape.domain.time.service;

import java.util.List;

import roomescape.domain.time.domain.Time;

public interface TimeService {

    public Time createTime(Time time);
    public List<Time> getAllTime();
    public boolean deleteTime(Long id);
}
