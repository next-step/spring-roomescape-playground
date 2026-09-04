package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.exception.DuplicateReservationTimeException;
import roomescape.exception.NotFoundReservationTimeException;
import roomescape.exception.ReservationTimeInUseException;

@Service
public class ReservationTimeService {

  private final ReservationTimeDao reservationTimeDao;
  private final ReservationDao reservationDao;

  public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
    this.reservationTimeDao = reservationTimeDao;
    this.reservationDao = reservationDao;
  }

  public List<ReservationTime> findAll() {
    return reservationTimeDao.findAll();
  }

  public ReservationTime create(ReservationTime reservationTime) {
    if (reservationTimeDao.existsByTime(reservationTime.time())) {
      throw new DuplicateReservationTimeException("이미 등록된 예약 시간입니다.");
    }
    return reservationTimeDao.save(reservationTime);
  }

  public void delete(Long id) {
    if (reservationDao.existsByTimeId(id)) {
      throw new ReservationTimeInUseException("예약이 등록된 시간은 삭제할 수 없습니다.");
    }
    if (reservationTimeDao.delete(id) == 0) {
      throw new NotFoundReservationTimeException("해당 id의 예약시간이 존재하지 않습니다.");
    }
  }
}
