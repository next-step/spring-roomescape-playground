package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.model.Reservation;

@Controller
public class ReservationController {
  private List<Reservation> reservations = new ArrayList<>();

  @GetMapping("/reservation")
  public String reservation() {
    return "reservation";
  }

  @GetMapping("/reservations")
  @ResponseBody
  public String getReservation(Model model) {
    reservations.add(new Reservation(
      1L, 
      "브라운", 
      "2023-01-01", 
      "10:00"
    ));
    
    reservations.add(new Reservation(
      2L, 
      "브라운", 
      "2023-01-02", 
      "11:00"
    ));

    model.addAttribute("reservations", reservations);

    return "reservation";
  }
}
