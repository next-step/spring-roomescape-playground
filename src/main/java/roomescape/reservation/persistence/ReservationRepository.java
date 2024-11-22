package roomescape.reservation.persistence;

import roomescape.reservation.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservation findById(Long reservationId);

    List<Reservation> findAll();

    void delete(Long reservationId);

}
