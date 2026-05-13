package roomescape.reservation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationId;

import java.net.URI;
import java.util.Collection;
import roomescape.reservation.dto.ReservationRequest;

@Controller
public class ReservationController {
	private final ReservationRepository reservationRepository;

	public ReservationController(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
	
	@GetMapping("/reservation")
	public String reservationPage() {
		return "reservation";
	}
	
	@GetMapping("/reservations")
	public ResponseEntity<Collection<Reservation>> getReservations() {
		return ResponseEntity.ok(reservationRepository.findAll());
	}

	@PostMapping("/reservations")
	public ResponseEntity<Reservation> createReservation(
		@Valid @RequestBody ReservationRequest request
	) {

		Reservation newReservation = reservationRepository.save(request.toReservation());
		
		return ResponseEntity
				.created(URI.create("/reservations/" + newReservation.getId()))
				.body(newReservation);
	}
	
	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
		ReservationId reservationId = new ReservationId(id);
		reservationRepository.deleteById(reservationId);
		
		return ResponseEntity.noContent().build();
	}
}
