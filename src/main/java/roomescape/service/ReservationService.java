package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationSaveRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

	private final List<Reservation> reservations = new ArrayList<>();
	private AtomicLong index = new AtomicLong(0);

	public List<ReservationResponse> getReservations() {
		return reservations.stream()
			.map(ReservationResponse::from)
			.toList();
	}

	public ResponseEntity<ReservationResponse> saveReservation(ReservationSaveRequest request) {
		Reservation reservation = new Reservation(index.incrementAndGet(), request.getName(), request.getDate(),
			request.getTime());
		reservations.add(reservation);
		return ResponseEntity.status(HttpStatus.CREATED)
			.header("Location", "/reservations/" + index.get())
			.body(ReservationResponse.from(reservation));
	}

	public ResponseEntity<Void> deleteReservation(Long id) {
		reservations.removeIf(reservation -> reservation.getId() == id);
		return ResponseEntity.noContent().build();
	}
}
