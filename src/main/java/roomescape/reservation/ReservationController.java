package roomescape.reservation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.reservation.dto.ReservationCreateResponse;
import roomescape.reservation.dto.ReservationSelectResponse;

@Controller
public class ReservationController {
	private final ReservationRepository reservationRepository;

	public ReservationController(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
	
	@GetMapping("/reservation")
	public String reservationPage() {
		return "new-reservation";
	}
	
	@GetMapping("/reservations")
	public ResponseEntity<Collection<ReservationSelectResponse>> getReservations() {
		return ResponseEntity.ok(reservationRepository.findAll());
	}

	@PostMapping("/reservations")
	public ResponseEntity<ReservationCreateResponse> createReservation(
		@Valid @RequestBody ReservationCreateRequest request
	) {

		ReservationCreateResponse response = reservationRepository.save(request);
		
		return ResponseEntity
				.created(URI.create("/reservations/" + response.id()))
				.body(response);
	}
	
	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
		reservationRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
