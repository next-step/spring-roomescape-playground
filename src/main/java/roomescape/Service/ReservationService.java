package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.DAO.ReservationReposiory;
import roomescape.DAO.TimeRepository;
import roomescape.Domain.Reservation;

import java.util.List;
import roomescape.DTO.ReservationDTO;
import roomescape.Domain.Time;

@Service
public class ReservationService {
  @Autowired
  private ReservationReposiory reservationReposiory;
  @Autowired
  private TimeRepository timeRepository;

  public List<Reservation> getAllReservations() {
    return reservationReposiory.getAllReservation();
  }

  public Reservation newReservation(ReservationDTO reservation) {
    String existTime = timeRepository.findTimeById(reservation.time());
    Time time = new Time(reservation.time(), existTime);
    return reservationReposiory.makeNewReservation(new Reservation(null, reservation.name(), reservation.date(), time));
  }

  public boolean deleteReservation(Long id) {
    Long exist = reservationReposiory.findReservationById(id);
    if (exist == null)
      return false;
    reservationReposiory.deleteReservationById(id);
    return true;
  }
}
