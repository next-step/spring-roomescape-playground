package roomescape.reservation.persistence;

import java.util.List;

import roomescape.reservation.domain.Reservation;

public interface ReservationDAO {

	List<Reservation> findAll();

	Reservation save(Reservation reservation);

	Reservation findById(Long id);

	void delete(Long id);
}
