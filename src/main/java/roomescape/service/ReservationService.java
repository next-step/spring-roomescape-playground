package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository reservationRepository;


  public List<Reservation> searchAllReservations() {
    return reservationRepository.findAll();
  }

  public Reservation makeReservation(Reservation reservation) {
    return reservationRepository.create(reservation);
  }

  public void cancelReservation(Long id) {
    reservationRepository.deleteById(id);
  }
}
