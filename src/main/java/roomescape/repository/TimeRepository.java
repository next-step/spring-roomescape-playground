package roomescape.repository;

import roomescape.model.ReservationTime;

import java.util.List;

public interface TimeRepository {

    ReservationTime timeAdd(ReservationTime time);

    List<ReservationTime> findAll();

    void delete(int id);
}
