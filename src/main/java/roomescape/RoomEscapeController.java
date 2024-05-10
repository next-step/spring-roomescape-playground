package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomEscapeController {

	@GetMapping("/")
	public String renderHomePage() {
		return "home";
	}
}
