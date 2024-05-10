package roomescape;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import roomescape.domain.Reservation;

@Controller
public class RoomEscapeController {

	private List<Reservation> reservations = new ArrayList<>();

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
	public List<Reservation> getReservations() {
		reservations.add(new Reservation(1, "삼겹살", "2024-05-10", "17:00"));
		reservations.add(new Reservation(2, "치킨", "2024-05-10", "20:00"));
		reservations.add(new Reservation(3, "피자", "2024-05-10", "22:00"));
		return reservations;
	}
}
