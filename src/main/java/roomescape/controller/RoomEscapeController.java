package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}
