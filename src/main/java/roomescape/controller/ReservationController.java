package roomescape.controller;

import static roomescape.dto.ReservationResponseDTO.AddReservationResponse;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDTO;
import roomescape.dto.ReservationResponseDTO.QueryReservationResponse;
import roomescape.service.ReservationService;

@RestController
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	@GetMapping("/reservation")
	public String reservation() {
		return "reservation";
	}

	@GetMapping("/reservations")
	public ResponseEntity<List<QueryReservationResponse>> getReservations() {
		return ResponseEntity.ok(reservationService.getReservations());
	}

	@PostMapping("/reservations")
	public ResponseEntity<AddReservationResponse> addReservation(
			@Valid @RequestBody ReservationRequestDTO.AddReservationRequest reservationRequest) {
		AddReservationResponse newReservation = reservationService.addReservation(reservationRequest);
		return ResponseEntity.created(URI.create("/reservations/" + newReservation.id())).body(newReservation);
	}

	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
		reservationService.deleteReservation(id);
		return ResponseEntity.noContent().build();
	}
}