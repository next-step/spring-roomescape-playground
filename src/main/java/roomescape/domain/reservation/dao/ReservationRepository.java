package roomescape.domain.reservation.dao;

import java.util.List;
import roomescape.domain.reservation.model.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    void save(Reservation reservation);

    Reservation findById(long reservationId);

    void delete(Reservation reservation);

    void deleteById(long reservationId);

    void clear();
}
