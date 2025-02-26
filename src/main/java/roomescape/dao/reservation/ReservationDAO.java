package roomescape.dao.reservation;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationDAO {

    Reservation create(Reservation reservation);

    List<Reservation> getAll();

    void delete(long id);

    Reservation getById(long id);

}
