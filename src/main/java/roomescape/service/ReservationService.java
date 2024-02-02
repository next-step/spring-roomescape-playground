package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDao;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    public ReservationService(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }
    public List<Reservation> getAllReservations(){
        return reservationDao.getAllReservations();
    }

    public Reservation saveReservation(Reservation reservation){
        int id = reservationDao.save(reservation);
        reservation.setId(id);
        return reservation;
    }
    public void deleteReservation(int id){
        reservationDao.deleteReservationById(id);
    }
    public  Reservation getReservationById(int id){
        return reservationDao.getReservationById(id);
    }
}
