package roomescape.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao reservationDao;

    public ReservationRepositoryImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDao.readAll();
    }

    @Override
    public boolean existsById(Long id) {
        return reservationDao.read(id) != null;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationDao.create(reservation);
    }

    @Override
    public void deleteById(Long id) {
        reservationDao.delete(id);
    }
}
