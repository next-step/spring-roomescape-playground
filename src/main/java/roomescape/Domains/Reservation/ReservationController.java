package roomescape.Domains.Reservation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

  @GetMapping("/reservation")
  public String index() {
    return "new-reservation";
  }
}
