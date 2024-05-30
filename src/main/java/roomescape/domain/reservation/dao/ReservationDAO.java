package roomescape.domain.reservation.dao;

import java.util.List;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.dto.ResponseReservationDTO;
import roomescape.domain.reservation.dto.ReservationDTO;

public interface ReservationDAO {
    List<ReservationDTO> findAll();
    ResponseReservationDTO insert(Reservation reservation);
    void deleteById(long id);
}
