package roomescape.domain.reservation.service;

import java.util.List;

import roomescape.domain.reservation.domain.Reservation;

public interface ReservationService {
    public Reservation addReservation(Reservation reservation);
    public void deleteReservation(Long id);
    public Reservation getReservationById(Long id);
    public List<Reservation> getAllReservation();

}
