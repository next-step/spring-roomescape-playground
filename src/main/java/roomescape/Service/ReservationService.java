package roomescape.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.DAO.ReservationDAO;
import roomescape.Domain.Reservation;

@Component
public class ReservationService {

  private final ReservationDAO reservationDAO;

  public ReservationService(ReservationDAO reservationDAO) {
    this.reservationDAO = reservationDAO;
  }

  public List<Reservation> getAllReservations() {
    return reservationDAO.findAllReservations();
  }

  public Reservation getReservationByID(Long id) {
    return reservationDAO.findReservationByID(id);
  }

  public List<Reservation> getReservationsByID(Long id) {
    return reservationDAO.findReservationsByID(id);
  }

  public Long addReservationByKey(Reservation reservation) {
    return reservationDAO.insertWithKeyHolder(reservation);
  }

  public void deleteReservationByID(Long id) {
    reservationDAO.delete(id);
  }
}
