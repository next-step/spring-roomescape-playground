package roomescape.repository;

import roomescape.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation reservationAdd(Reservation reservation);

    List<Reservation> findAll();

    void delete(int id);
}
