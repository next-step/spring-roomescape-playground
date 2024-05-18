package roomescape.domain.dao;

import java.util.List;
import roomescape.domain.RequestReservationDTO;
import roomescape.domain.Reservation;

public interface ReservationDAO {
    List<?> findAll();
    Reservation insert(Reservation reservation);
    void deleteById(long id);
}
