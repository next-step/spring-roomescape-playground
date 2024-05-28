package roomescape.reservation.domain;

import java.util.List;


public interface ReservationRepository {
    public Reservation save(Reservation reservation);

    public List<Reservation> findAll();

    public void deleteReservation(Long id);
}
