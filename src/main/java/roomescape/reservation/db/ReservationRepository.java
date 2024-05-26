package roomescape.reservation.db;

import roomescape.reservation.model.ReservationRequest;

import java.util.List;


public interface ReservationRepository {


    List<ReservationEntity> findAllList();

    int countReservation();

    ReservationEntity findReservationById(Long id);

    ReservationEntity insertReservation(ReservationRequest reservationRequest);

    void deleteReservation(Long id);
}
