package roomescape.domain.Repository.reservationRepository;

import roomescape.domain.Model.Reservation;

import java.util.List;


public interface ReservationRepository {
    public Reservation save(Reservation reservation);

    public List<Reservation> findAll();

    public void deleteReservation(Long id);
}
