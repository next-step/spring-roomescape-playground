package roomescape.reservation.domain;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.exception.NotFoundException;

public class ReservationList {
	Map<ReservationId, Reservation> reservations = new HashMap<>();
	private final AtomicLong nextId = new AtomicLong(0);

	public Collection<Reservation> get() {
		return reservations.values();
	}
	
	public Reservation add(Reservation reservation) {
		validate(reservation);
		Reservation newReservation = Reservation.toEntity(reservation, new ReservationId(nextId.incrementAndGet()));
		reservations.put(newReservation.id(), newReservation);

		return newReservation;
	}
	
	public void remove(ReservationId id) {
		if (!reservations.containsKey(id)) {
			throw new NotFoundException("존재하지 않는 예약입니다.");
		}

		reservations.remove(id);
	}

	private void validate(Reservation reservation) {
		if (reservation.name() == null) {
			throw new NotFoundException("name은 비어있을 수 없습니다.");
		}

		if (reservation.date() == null) {
			throw new NotFoundException("date는 비어있을 수 없습니다.");
		}

		if (reservation.time() == null) {
			throw new NotFoundException("time은 비어있을 수 없습니다.");
		}
	}
}
