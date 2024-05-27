package roomescape.repository;

import roomescape.model.Time;

import java.util.List;

public interface TimeRepository {

    Time timeAdd(Time time);

    List<Time> findAll();

    void delete(int id);
}
