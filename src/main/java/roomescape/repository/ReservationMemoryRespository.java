package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;

@Repository
public class ReservationMemoryRespository implements ReservationRepository {

	private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
	private final AtomicLong index = new AtomicLong(0);

	@Override
	public List<Reservation> findAll() {
		return List.copyOf(reservations.values());
	}

	@Override
	public void save(Reservation reservation) {
		reservation.setId(index.incrementAndGet());
		reservations.put(reservation.getId(), reservation);
	}

	@Override
	public Reservation findById(Long id) {
		return reservations.get(id);
	}

	@Override
	public void delete(Long id) {
		reservations.remove(id);
	}
}
