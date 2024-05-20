package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import roomescape.domain.Reservation;

@org.springframework.stereotype.Repository
public class ReservationMemoryRepository implements ReservationRepository {

	private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
	private final AtomicLong index = new AtomicLong(0);

	@Override
	public List<Reservation> findAll() {
		return List.copyOf(reservations.values());
	}

	@Override
	public Reservation save(Reservation reservation) {
		final Long id = index.incrementAndGet();
		reservations.put(id, reservation);
		return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
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
