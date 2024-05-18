package roomescape.repository;

import java.util.List;

import roomescape.domain.Reservation;

public interface Repository {

	List<Reservation> findAll();

	void save(Reservation reservation);

	Reservation findById(Long id);

	void delete(Long id);
}
