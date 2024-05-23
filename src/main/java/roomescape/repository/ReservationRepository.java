package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    void addReservation(Reservation reservation);

    List<Reservation> findAll();

    void deleteReservationById(Long id);
}
