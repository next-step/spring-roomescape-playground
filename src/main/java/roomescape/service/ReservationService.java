package roomescape.service;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationService {

  Reservation findById(Long id);
  List<Reservation> findAll();
  Reservation create(Reservation reservation);
  Reservation delete(final Long id);
}
