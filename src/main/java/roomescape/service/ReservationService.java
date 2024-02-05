package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationQueryingDAO;
import roomescape.dao.ReservationUpdatingDAO;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationAddRequest;
import roomescape.dto.ReservationDTO;
import roomescape.exception.NotFoundException;

@Service
public class ReservationService {

    private final ReservationQueryingDAO reservationQueryingDAO;

    private final ReservationUpdatingDAO reservationUpdatingDAO;

    @Autowired
    public ReservationService(final ReservationQueryingDAO reservationQueryingDAO, final ReservationUpdatingDAO reservationUpdatingDAO){

        this.reservationQueryingDAO = reservationQueryingDAO;
        this.reservationUpdatingDAO = reservationUpdatingDAO;

    }


    public List<ReservationDTO> findAllReservations() {
        return reservationQueryingDAO.getAllReservations();
    }

    public ReservationDTO bookReservation(ReservationAddRequest reservation) {

        Reservation newReservation = new Reservation();

        newReservation.setName(reservation.getName());
        newReservation.setDate(reservation.getDate());
        newReservation.setTime(new Time(reservation.getTime().getTime()));

        Number reservationId = reservationUpdatingDAO.save(newReservation);

        return ReservationDTO.toEntity(reservationId.longValue(), newReservation.getName(), newReservation.getDate(), newReservation.getTime());

    }

    public void delete(long id) {

        int row = reservationUpdatingDAO.delete(id);

        if(row == 0)throw new NotFoundException("There is no Reservation");

    }
}
