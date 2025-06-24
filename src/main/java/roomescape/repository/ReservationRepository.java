package roomescape.repository;

import roomescape.model.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    boolean delete(int id);
}
