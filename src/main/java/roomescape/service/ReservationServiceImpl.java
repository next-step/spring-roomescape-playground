package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Reservation.NotFoundReservationException;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationDao reservationDao;
  private final TimeDao timeDao;

  @Override
  public List<Reservation> getReservations() {
    return reservationDao.findAll();
  }

  @Override
  public Reservation addReservation(final ReservationRequestDto reservationRequestDto) {
    Reservation reservation = reservationDao.save(reservationRequestDto);
    Time time = timeDao.findById(Long.parseLong(reservationRequestDto.getTime())).orElseThrow();
    reservation.setTime(time);
    return reservation;
  }

  @Override
  public void removeReservation(final Long id) {
    if (reservationDao.delete(id) == 0)
      throw new NotFoundReservationException();
  }
}
