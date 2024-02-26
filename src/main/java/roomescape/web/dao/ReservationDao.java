package roomescape.web.dao;

import roomescape.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    List<Reservation> getAllReservations();

    Reservation createReservation(Long Id, String name, String date, String time);

    void deleteReservationById(Long id);

    Optional<Reservation> getReservationById(Long id);
}
