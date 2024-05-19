package roomescape.repository;

import java.util.List;

import roomescape.domain.Reservation;

public interface ReservationRepository {

	List<Reservation> findAll();

	void save(Reservation reservation);

	Reservation findById(Long id);

	void delete(Long id);
}
