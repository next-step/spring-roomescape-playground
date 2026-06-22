package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dao.ReservationDao;

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

    public boolean existsByDateAndTimeId(String date, Long timeId) {
        return reservationDao.existsByDateAndTimeId(date, timeId);
    }

    public Reservation save(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    public boolean deleteById(Long id) {
        return reservationDao.deleteById(id);
    }
}
