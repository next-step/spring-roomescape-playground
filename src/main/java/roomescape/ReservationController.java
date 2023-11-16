package roomescape;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

  private List<Reservation> reservations = new ArrayList<>();

  @GetMapping("/reservation")
  public String reservation(Model model) throws ParseException {
    if(reservations.size() == 0){
      LocalDate date1 = LocalDate.of(2023, 1, 1);
      LocalTime time1 = LocalTime.of(10, 0, 0);
      Reservation reservation1 = new Reservation(1L, "브라운", date1, time1);
      reservations.add(reservation1);

      LocalDate date2 = LocalDate.of(2023, 1, 1);
      LocalTime time2 = LocalTime.of(10, 0, 0);
      Reservation reservation2 = new Reservation(2L, "브라운", date2, time2);
      reservations.add(reservation2);

      LocalDate date3 = LocalDate.of(2023, 1, 1);
      LocalTime time3 = LocalTime.of(10, 0, 0);
      Reservation reservation3 = new Reservation(3L, "브라운", date3, time3);
      reservations.add(reservation3);
    }

    return "reservation";
  }

  @GetMapping("/reservations")
  public ResponseEntity<List<Reservation>> read() {
    return ResponseEntity.ok().body(reservations);
  }

}
