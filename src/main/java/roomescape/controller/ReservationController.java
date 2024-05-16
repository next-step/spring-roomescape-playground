package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dto.request.ReservationSaveRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping("/reservations")
	public List<ReservationResponse> getReservations() {
		return reservationService.getReservations();
	}

	@PostMapping("/reservations")
	public ResponseEntity<ReservationResponse> saveReservation(@RequestBody ReservationSaveRequest request) {
		ReservationResponse response = reservationService.saveReservation(request);
		URI location = URI.create("/reservations/" + response.id());
		return ResponseEntity.created(location).body(response);
	}

	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
		reservationService.deleteReservation(id);
		return ResponseEntity.noContent().build();
	}
}
