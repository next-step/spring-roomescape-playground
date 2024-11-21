package roomescape.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.entity.Reservation;

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

    //예약 추가
    public Reservation insert(Reservation reservation) {
        return reservationDao.insert(reservation);
    }

    //예약 삭제
    public void delete(Long id) {
        reservationDao.delete(id);
    }
}

