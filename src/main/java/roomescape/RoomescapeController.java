package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomescapeController {

  private List<Reservation> reservations = new ArrayList<>();
  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/reservation")
  public String reservation() {
    return "reservation";
  }

  @GetMapping("/reservations")
  @ResponseBody
  public List<Reservation> reservationList() {
    return reservations;
  }
}
