package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.NotFoundReservationException;

@Service
public class ReservationService {

  private final ReservationDao reservationDao;
  private final ReservationTimeDao reservationTimeDao;

  public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
    this.reservationDao = reservationDao;
    this.reservationTimeDao = reservationTimeDao;
  }

  public List<Reservation> findAll() {
    return reservationDao.findAll();
  }

  public Reservation create(String name, LocalDate date, Long timeId) {
    ReservationTime time = requireReservationTime(timeId);
    Reservation reservation = new Reservation(null, name, date, time);
    return reservationDao.save(reservation);
  }

  public void delete(Long id) {
    if (reservationDao.delete(id) == 0) {
      throw new NotFoundReservationException("해당 id의 예약이 존재하지 않습니다.");
    }
  }

  private ReservationTime requireReservationTime(Long timeId) {
    ReservationTime time = reservationTimeDao.findById(timeId).orElse(null);
    if (time == null) {
      throw new NotFoundReservationException("존재하지 않는 예약 시간입니다.");
    }
    return time;
  }
}
