package roomescape.reservation.repository;

import roomescape.reservation.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    public Optional<Reservation> findReservationById(Long id);
    public List<Reservation> findAllReservations();
    public Long insertWithKeyHolder(Reservation reservation);
    public int delete(Reservation reservation);
}