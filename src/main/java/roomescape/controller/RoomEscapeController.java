package roomescape.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import roomescape.dto.request.ReservationSaveRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@Controller
public class RoomEscapeController {

	private final ReservationService reservationService;

	public RoomEscapeController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping("/")
	public String renderHomePage() {
		return "home";
	}

	@GetMapping("/reservation")
	public String renderReservationPage() {
		return "reservation";
	}

	@GetMapping("/reservations")
	@ResponseBody
	public List<ReservationResponse> getReservations() {
		return reservationService.getReservations();
	}

	@PostMapping("/reservations")
	@ResponseBody
	public ResponseEntity<ReservationResponse> saveReservation(@RequestBody ReservationSaveRequest request) {
		return reservationService.saveReservation(request);
	}

	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
		return reservationService.deleteReservation(id);
	}

}
