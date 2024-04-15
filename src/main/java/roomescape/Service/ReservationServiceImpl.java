package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.NotFoundReservationException;

import java.util.List;



@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final TimeService timeService; // TimeService 주입

    @Autowired
    public ReservationServiceImpl(ReservationDao reservationDao, TimeService timeService) {
        this.reservationDao = reservationDao;
        this.timeService = timeService;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationDao.getAllReservations();
    }

    @Transactional
    public ResponseEntity<Reservation> addReservation(Reservation reservation) {
        Time reservationTime = reservation.getTime();
        Time existingTime = timeService.getTimeByTime(reservationTime.getTime());

        if (existingTime == null) {
            throw new NotFoundReservationException("Time not found: " + reservationTime.getTime());
        }

        reservation.setTime(existingTime);
        long newId = reservationDao.addReservation(reservation);
        Reservation addedReservation = reservationDao.getReservationById(newId);

        return ResponseEntity.ok().body(addedReservation);
    }


    @Override
    public void cancelReservation(Long id) {
        reservationDao.cancelReservation(id);
    }
}
