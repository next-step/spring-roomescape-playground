package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationList;

import java.net.URI;
import java.util.Collection;

@Controller
public class ReservationController {
	private final ReservationList reservationList = new ReservationList();
	
	@GetMapping("/reservation")
	public String reservationPage() {
		return "reservation";
	}
	
	@GetMapping("/reservations")
	public ResponseEntity<Collection<Reservation>> getReservations() {
		return ResponseEntity.ok(reservationList.get());
	}
	
	@PostMapping("/reservations")
	public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
		Reservation newReservation = reservationList.add(reservation);
		
		return ResponseEntity
				.created(URI.create("/reservations/" + newReservation.getId()))
				.body(newReservation);
	}
	
	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
		reservationList.remove(id);
		
		return ResponseEntity.noContent().build();
	}
}
