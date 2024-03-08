package roomescape.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.exception.ReservationException;

@Repository
public class ReservationRepository {
	private final Map<Long, Reservation> reservations;
	private final AtomicLong id;

	public ReservationRepository() {
		this.reservations = new HashMap<>();
		this.id = new AtomicLong(0);
	}

	public Collection<Reservation> findAll() {
		return List.copyOf(reservations.values());
	}

	public Long save(Reservation reservation) {
		Long newId = id.incrementAndGet();
		reservations.put(newId, reservation);
		return newId;
	}

	public void deleteById(Long id) {
		if (!reservations.containsKey(id)) {
			throw new ReservationException("Reservation with id " + id + " not found.");
		}
		reservations.remove(id);
	}
}
