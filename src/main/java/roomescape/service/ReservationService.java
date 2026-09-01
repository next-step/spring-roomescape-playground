package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;

@Service
public class ReservationService {

  private final ReservationDao reservationDao;

  public ReservationService(ReservationDao reservationDao) {
    this.reservationDao = reservationDao;
  }

  public List<Reservation> findAll() {
    return reservationDao.findAll();
  }

  public Reservation create(Reservation reservation) {
    return reservationDao.save(reservation);
  }

  public void delete(Long id) {
    if (reservationDao.delete(id) == 0) {
      throw new NotFoundReservationException("해당 id의 예약이 존재하지 않습니다.");
    }
  }
}