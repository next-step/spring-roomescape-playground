package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationResponseDTO;

@Repository
public class ReservationRepository {
	private final List<ReservationResponseDTO.QueryReservation> reservations = new ArrayList<>();
	private final AtomicLong id = new AtomicLong(1);

	public List<ReservationResponseDTO.QueryReservation> findAll() {
		return reservations;
	}

	public ReservationResponseDTO.QueryReservation save(ReservationResponseDTO.QueryReservation reservation) {
		reservations.add(reservation);
		return reservation;
	}

	public void deleteById(Long id) {
		reservations.removeIf(reservation -> reservation.getId().equals(id));
	}

	public Long generateId() {
		return id.incrementAndGet();
	}

	public void clear() {
		reservations.clear();
		id.set(0);
	}
}
