package roomescape.domain.reservation;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.dao.ReservationDAO;
import roomescape.domain.reservation.dto.RequestReservationDTO;
import roomescape.domain.reservation.dto.ReservationDTO;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;

    public ReservationService(@Qualifier("jdbcReservationDAO") ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<ReservationDTO> findAll() {
        return reservationDAO.findAll();
    }

    public ResponseReservation create(RequestReservationDTO request) {
        Reservation reservation = request.toReservaiton();
        return reservationDAO.insert(reservation);
    }

    public void delete(long id) {
        reservationDAO.deleteById(id);
    }
}
