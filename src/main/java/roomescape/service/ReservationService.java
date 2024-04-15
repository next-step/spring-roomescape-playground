package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationDTO;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.repository.ReservationDAO;
import roomescape.repository.TimeDAO;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private TimeDAO timeDAO;

    public List<Reservation> findAllReservations() {
        return reservationDAO.findAll();
    }

    public Reservation saveReservation(ReservationDTO reservationDTO) {
        // Time Entity
        Time time = timeDAO.findTimeById(reservationDTO.getTimeId());
        // Reservation Entity
        Reservation reservation = ReservationDTO.toEntity(reservationDTO, time, null);
        Long id = reservationDAO.insertWithKeyHolder(reservation);
        reservation.setId(id);

        return reservation;
    }

    public void deleteReservation(Long id) {
        reservationDAO.delete(id);
    }

}
