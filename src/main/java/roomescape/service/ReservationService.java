package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationQueryingDAO;
import roomescape.dao.ReservationUpdatingDAO;
import roomescape.dao.TimeQueryingDAO;
import roomescape.dao.TimeUpdatingDAO;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Service
public class ReservationService {
//    @Autowired
    ReservationQueryingDAO reservationQueryingDAO;

//    @Autowired
    ReservationUpdatingDAO reservationUpdatingDAO;


    public ReservationService(final ReservationQueryingDAO reservationQueryingDAO, final ReservationUpdatingDAO reservationUpdatingDAO, TimeQueryingDAO timeQueryingDAO, TimeUpdatingDAO timeUpdatingDAO){
        this.reservationQueryingDAO = reservationQueryingDAO;
        this.reservationUpdatingDAO = reservationUpdatingDAO;

    }


    public List<Reservation> findAllReservations() {
        return reservationQueryingDAO.getAllReservations();
    }

    public Reservation bookReservation(Reservation reservation) {

        Reservation newReservation = new Reservation();

        newReservation.setName(reservation.getName());
        newReservation.setDate(reservation.getDate());
        newReservation.setTime(new Time(reservation.getTime().getTime()));

        Number reservationId = reservationUpdatingDAO.save(newReservation);

        return Reservation.toEntity(reservationId.longValue(), newReservation);


    }

    public int delete(long id) {
        return reservationUpdatingDAO.delete(id);
    }
}
