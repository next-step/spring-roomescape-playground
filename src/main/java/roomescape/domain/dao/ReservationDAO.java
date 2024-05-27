package roomescape.domain.dao;

import java.util.List;
import roomescape.domain.RequestReservationDTO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDTO;

public interface ReservationDAO {
    List<ReservationDTO> findAll();
    Reservation insert(Reservation reservation);
    void deleteById(long id);
}
