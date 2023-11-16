package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String Reservation (Model model){
        reservations = new ArrayList<>();
        reservations.add(new Reservation(1,"브라운1", "2023-01-01", "10:00"));
        reservations.add( new Reservation(2,"브라운2", "2023-01-01", "11:00"));
        reservations.add(new Reservation(3,"브라운3", "2023-01-01", "12:00"));
        model.addAttribute("reservations",reservations);
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> Reservations (){
        return ResponseEntity.ok().body(reservations);
    }

}
