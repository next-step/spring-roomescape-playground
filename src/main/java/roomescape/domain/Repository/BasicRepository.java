package roomescape.domain.Repository;

import roomescape.domain.Model.Reservation;

import java.util.List;


public interface BasicRepository {
    public Reservation save(Reservation reservation);

    public List<Reservation> findAll();

    public void deleteReservation(Long id);
}
