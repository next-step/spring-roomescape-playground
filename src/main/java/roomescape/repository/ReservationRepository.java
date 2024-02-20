package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationResponseDTO;
import roomescape.dto.ReservationResponseDTO.QueryReservation;
import roomescape.exception.ReservationException;

@Repository
public class ReservationRepository {
	private final List<ReservationResponseDTO.QueryReservation> reservations;
	private final AtomicLong id;

	public ReservationRepository() {
		this.reservations = new ArrayList<>();
		this.id = new AtomicLong(0);
	}

	public List<ReservationResponseDTO.QueryReservation> findAll() {
		return reservations;
	}

	public void save(QueryReservation reservation) {
		reservations.add(reservation);
	}

	public void deleteById(Long id) {
		boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));
		if (!removed) {
			throw new ReservationException("Reservation with id " + id + " not found.");
		}
	}

	public Long generateId() {
		return id.incrementAndGet();
	}
}
