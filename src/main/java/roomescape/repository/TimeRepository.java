package roomescape.repository;

import roomescape.model.Time;

import java.util.List;

public interface TimeRepository {

    Time save(Time time);

    boolean delete(int id);

    List<Time> findAll();

    Time findById(int id);

    Time findByTime(String timeValue);
}
