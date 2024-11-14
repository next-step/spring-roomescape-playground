package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.handler.ErrorStatus;
import roomescape.handler.exception.ReservationException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationDao reservationDao;


  @Override
  public Reservation create(Reservation reservation) {
    return reservationDao.save(reservation);
  }

  @Override
  public Reservation findById(Long id) {

    return reservationDao.findById(id)
        .orElseThrow(() -> new ReservationException(ErrorStatus._NOT_FOUND_RESERVAIOTN));

  }

  @Override
  public Reservation delete(Long id) {

    Reservation reservation = this.findById(id);
    reservationDao.remove(reservation);

    return reservation;
  }

  @Override
  public List<Reservation> findAll() {
    return reservationDao.findAll();
  }
}

