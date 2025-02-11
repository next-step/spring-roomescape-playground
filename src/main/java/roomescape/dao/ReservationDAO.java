package roomescape.dao;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationDAO {

    void save(Reservation reservation);

    List<Reservation> findAll();

    void update(Reservation reservation);

    void delete(long id);

    int count();
}