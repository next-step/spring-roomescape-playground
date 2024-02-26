package roomescape.Service;

import roomescape.Domain.Reservation;

import java.util.List;

public interface ReservationService {
    public List<Reservation> getReservations();
    public Long createReservation(Reservation reservation);
    public void deleteReservation(Long id);
}
