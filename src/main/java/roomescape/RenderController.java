package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RenderController {

	@GetMapping("/")
	public String renderHomePage() {
		return "home";
	}

	@GetMapping("/reservation")
	public String renderReservationPage() {
		return "new-reservation";
	}

	@GetMapping("/roomescape/time")
	public String renderTimePage() {
		return "time";
	}
}
