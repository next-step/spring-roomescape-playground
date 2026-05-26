package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.DAO.ReservationDao;
import roomescape.DAO.TimeDao;
import roomescape.domain.Reservation;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;
    private final ValidTimeChecker timeChecker;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
        this.timeChecker = new ValidTimeChecker(timeDao);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    @Transactional
    public List<LocalTime> getValidTimesByDate(LocalDate date) {
        timeDao.refreshValidTimesScheduler();
        return timeDao.findAllValidTimes(date);
    }

    @Transactional
    public Reservation createReservation(Reservation request) {
        timeChecker.checkReservationable(request.getDate(), request.getTime());

        Reservation savedReservation = reservationDao.saveReservation(request);
        timeDao.deleteValidTime(request.getDate(), request.getTime());

        return savedReservation;
    }

    @Transactional
    public void deleteReservation(int id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new ReservationException.NotFoundReservationException("해당 예약이 존재하지 않습니다.");
        }

        reservationDao.deleteById(id);
        timeDao.saveValidTime(reservation.getDate(), reservation.getTime());
    }
}
