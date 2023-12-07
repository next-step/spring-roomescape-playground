package roomescape.room;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.room.ReservationResponse.Read;

@Controller
public class ReservationController {

	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping("/reservation")
	public String getReservationPage() {
		return "new-reservation";
	}

	@PostMapping("/reservations")
	public ResponseEntity<ReservationResponse.Create> createRoom(
			@Valid @RequestBody ReservationRequest.Create request) {
		ReservationResponse.Create response = reservationService.createReservation(request);
		return ResponseEntity.created(URI.create("/reservations/" + response.id()))
				.body(response);
	}

	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
		reservationService.deleteReservationById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/reservations/{id}")
	public ResponseEntity<ReservationResponse.Read> getRoom(@PathVariable Long id) {
		ReservationResponse.Read response = reservationService.getReservation(id);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/reservations")
	public List<ReservationResponse.Read> getList() {
		List<Read> response = reservationService.getReservations();
		return response;
	}
}
