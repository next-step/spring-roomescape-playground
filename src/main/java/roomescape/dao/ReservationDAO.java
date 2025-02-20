package roomescape.dao;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationDAO {

    Reservation save(Reservation reservation);

    List<Reservation> getAll();

    void delete(long id);

    Reservation getById(long id);

}
