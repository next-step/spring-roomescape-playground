package roomescape.repository;

import roomescape.model.Reservation;
import roomescape.model.ReservationRequest;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    Reservation save(ReservationRequest reservationRequest);

    boolean delete(int id);

    Reservation findById(int id);
}
