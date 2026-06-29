package roomescape.reservation.repository;

import org.springframework.stereotype.Repository;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
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

    public boolean existsByDateAndTimeId(LocalDate date, Long timeId) {
        return reservationDao.existsByDateAndTimeId(date, timeId);
    }

    public Reservation save(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    public boolean deleteById(Long id) {
        return reservationDao.deleteById(id);
    }
}
