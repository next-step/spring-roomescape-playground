package roomescape.domain.reservation.dao;

import java.util.List;
import roomescape.domain.reservation.model.Reservation;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);

    void deleteById(long reservationId);

    List<Reservation> findAll();
}
