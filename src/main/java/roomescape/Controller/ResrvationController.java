package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.Domain.Reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ResrvationController {

    private List<Reservation> reservations = Arrays.asList(
            new Reservation(1, "red", "2024-01-10", "13:00"),
            new Reservation(2, "orange", "2024-01-11", "14:00")
            //new Reservation(3, "yellow", "2024-01-12", "15:00")
    );


    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations(){

        return ResponseEntity.ok(reservations);
    }

}
