package roomescape.web.dao;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    List<Reservation> getAllReservations();

    Reservation createReservation(Long Id, String name, String date, Time time);

    void deleteReservationById(Long id);

    Optional<Reservation> getReservationById(Long id);
}
