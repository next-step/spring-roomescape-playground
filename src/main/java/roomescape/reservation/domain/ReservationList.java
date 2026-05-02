package roomescape.reservation.domain;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationList {
	Map<ReservationId, Reservation> reservations = new HashMap<>();
	private final AtomicLong nextId = new AtomicLong(0);

	public Collection<Reservation> get() {
		return reservations.values();
	}
	
	public Reservation add(Reservation reservation) {
		Reservation newReservation = Reservation.toEntity(reservation, new ReservationId(nextId.incrementAndGet()));
		reservations.put(newReservation.id(), newReservation);

		return newReservation;
	}
	
	public void remove(ReservationId id) {
		reservations.remove(id);
	}
}
