package roomescape.domain.reservation.dao;

import java.util.List;
import roomescape.domain.reservation.entity.Reservation;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservation findById(long reservationId);

    void delete(Reservation reservation);

    void deleteById(long reservationId);

    List<Reservation> findAll();
}
