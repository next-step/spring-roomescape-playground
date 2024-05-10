package roomescape.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

	private final List<Reservation> reservations = new ArrayList<>();

	public List<ReservationResponse> getReservations() {
		reservations.add(new Reservation(1L, "삼겹살", "2024-05-10", "16:00"));
		reservations.add(new Reservation(2L, "치킨", "2024-05-10", "20:00"));
		reservations.add(new Reservation(3L, "피자", "2024-05-10", "22:00"));
		return reservations.stream()
			.map(ReservationResponse::from)
			.toList();
	}
}
