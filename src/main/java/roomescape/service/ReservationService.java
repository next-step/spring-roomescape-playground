package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ValidTimeChecker timeChecker;

    public ReservationService(ReservationDao reservationDao, ValidTimeChecker validTimeChecker) {
        this.reservationDao = reservationDao;
        this.timeChecker = validTimeChecker;
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<LocalTime> getInvalidTimes(LocalDate date) {
        return reservationDao.findAllReservationTimesByDate(date);
    }

    @Transactional
    public Reservation createReservation(Reservation request) {
        timeChecker.checkReservationable(request.getDate(), request.getTime());

        return reservationDao.saveReservation(request);
    }

    @Transactional(readOnly = true)
    public List<LocalTime> getAllRegisteredTimes() {
        return reservationDao.findAllTimes();
    }

    @Transactional
    public LocalTime registerNewTime(LocalTime time) {
        return reservationDao.saveTime(time);
    }

    @Transactional
    public void removeTime(int timeId) {
        reservationDao.deleteTimeById(timeId);
    }

    @Transactional
    public void deleteReservation(int id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new ReservationException.NotFoundReservationException("해당 예약이 존재하지 않습니다.");
        }

        reservationDao.deleteById(id);
    }
}
