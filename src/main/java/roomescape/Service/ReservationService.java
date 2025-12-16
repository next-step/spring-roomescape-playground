package roomescape.Service;

import org.springframework.stereotype.Service;
import roomescape.DAO.ReservationDao;
import roomescape.Domain.Reservation;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    public ReservationService(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll(){
        return reservationDao.findAll();
    }

    public Long add(Reservation reservation){
        return reservationDao.add(reservation);
    }

    public int deleteByid(Long id){
        return reservationDao.deleteByid(id);
    }
}
