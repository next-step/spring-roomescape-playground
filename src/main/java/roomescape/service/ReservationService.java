package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequest;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public Reservation add(ReservationRequest reservationRequest) {
        Time time = timeDao.getById(reservationRequest.time());
        Reservation reservation = new Reservation(reservationRequest.name(), reservationRequest.date(), time);
        return reservationDao.add(reservation);
    }

    public List<Reservation> getAll() {
        return reservationDao.getAll();
    }

    public void cancel(Long id) {
        reservationDao.deleteBy(id);
    }
}
