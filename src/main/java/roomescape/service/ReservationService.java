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
import roomescape.exception.NotFoundReservationException;

@Service
public class ReservationService {

	private final List<Reservation> reservations = new ArrayList<>();
	private AtomicLong index = new AtomicLong(0);

	public ResponseEntity<List<ReservationResponse>> getReservations() {
		List<ReservationResponse> response = reservations.stream()
			.map(ReservationResponse::from)
			.toList();
		return ResponseEntity.ok().body(
			response
		);

	}

	public ResponseEntity<ReservationResponse> saveReservation(ReservationSaveRequest request) {
		Reservation reservation = new Reservation(index.incrementAndGet(), request.name(), request.date(),
			request.time());
		reservations.add(reservation);
		return ResponseEntity.status(HttpStatus.CREATED)
			.header("Location", "/reservations/" + index.get())
			.body(ReservationResponse.from(reservation));
	}

	public ResponseEntity<Void> deleteReservation(Long id) {
		boolean isRemoved = reservations.removeIf(reservation -> reservation.getId() == id);
		if (!isRemoved) {
			throw new NotFoundReservationException("존재하지 않는 예약정보 입니다.");
		}
		return ResponseEntity.noContent().build();
	}
}
