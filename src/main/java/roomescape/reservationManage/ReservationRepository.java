package roomescape.reservationManage.repository;

import org.springframework.stereotype.Repository;
import roomescape.exception.NotFoundReservationException;
import roomescape.reservationManage.Reservation;
import roomescape.reservationManage.ReservationDao;

import java.util.List;

@Repository
public class ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation save(String name, String date, Long time) {
        return reservationDao.insert(name, date, time);
    }

    public Reservation findById(Long id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NotFoundReservationException("예약 ID가 존재하지 않습니다: " + id);
        }
        return reservation;
    }

    public void delete(Long id) {
        reservationDao.deleteById(id);
    }
}
