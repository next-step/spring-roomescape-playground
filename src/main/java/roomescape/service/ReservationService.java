package roomescape.service;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

public interface ReservationService {

  List<Reservation> getReservations();

  Reservation addReservation(ReservationRequestDto reservationRequestDto);

  void removeReservation(Long id);
}
