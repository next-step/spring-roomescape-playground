package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.exception.ReservationException;

@Repository
public class ReservationRepository {
	private final List<Reservation> reservations;
	private final AtomicLong id;

	public ReservationRepository() {
		this.reservations = new ArrayList<>();
		this.id = new AtomicLong(0);
	}

	public List<Reservation> findAll() {
		return reservations;
	}

	public void save(Reservation reservation) {
		reservations.add(reservation);
	}

	public void deleteById(Long id) {
		boolean removed = reservations.removeIf(reservation -> reservation.id().equals(id));
		if (!removed) {
			throw new ReservationException("Reservation with id " + id + " not found.");
		}
	}

	public Long generateId() {
		return id.incrementAndGet();
	}
}
