package roomescape.time.repository;

import roomescape.time.model.Time;

import java.util.List;
import java.util.Optional;

public interface TimeRepository {
    public Optional<Time> findTimeById(Long id);
    public Optional<Time> findTimeByValue(String value);
    public List<Time> findAllTime();
    public Long insertWithKeyHolder(Time time);
    public void delete(Time time);
}