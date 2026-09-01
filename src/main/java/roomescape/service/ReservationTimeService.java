package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.exception.NotFoundReservationException;

@Service
public class ReservationTimeService {

  private final ReservationTimeDao reservationTimeDao;

  public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
    this.reservationTimeDao = reservationTimeDao;
  }

  public List<ReservationTime> findAll() {
    return reservationTimeDao.findAll();
  }

  public ReservationTime create(ReservationTime reservationTime) {
    return reservationTimeDao.save(reservationTime);
  }

  public void delete(Long id) {
    if (reservationTimeDao.delete(id) == 0) {
      throw new NotFoundReservationException("해당 id의 예약시간이 존재하지 않습니다.");
    }
  }
}
