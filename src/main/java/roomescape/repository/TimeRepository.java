package roomescape.repository;

import roomescape.model.Time;

import java.util.List;

public interface TimeRepository {

    Time timeAdd(Time time);

    List<Time> findAll();

    Time findById(int id);

    void delete(int id);
}
