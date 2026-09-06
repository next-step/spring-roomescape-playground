package roomescape.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundReservationTimeException;
import roomescape.exception.ReservationInPastException;

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
    validateNotPast(date, time.getTime());
    Reservation reservation = new Reservation(null, name, date, time);
    if(reservationDao.existsByDateAndTimeId(date, timeId)){
      throw new DuplicateReservationException("이미 예약된 날짜와 시간에는 예약할 수 없습니다.");
    }
    return reservationDao.save(reservation);
  }

  private void validateNotPast(LocalDate date, LocalTime time) {
    if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now())) {
      throw new ReservationInPastException("지난 날짜와 시간은 예약할 수 없습니다.");
    }
  }

  public void delete(Long id) {
    if (reservationDao.delete(id) == 0) {
      throw new NotFoundReservationException("해당 id의 예약이 존재하지 않습니다.");
    }
  }

  private ReservationTime requireReservationTime(Long timeId) {
    ReservationTime time = reservationTimeDao.findById(timeId).orElse(null);
    if (time == null) {
      throw new NotFoundReservationTimeException("존재하지 않는 예약 시간입니다.");
    }
    return time;
  }
}
