package roomescape.dao;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationDAO {

    void save(Reservation reservation);

    List<Reservation> getAll();

    void update(Reservation reservation);

    void delete(long id);

    int count();

    Reservation getById(int id);

}