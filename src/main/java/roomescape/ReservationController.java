package roomescape;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    @GetMapping("/reservation")
    public void reservation () {
    }

    public List<Reservation> reservations = new ArrayList<>();


    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> read() {
        reservations.add(new Reservation(1,"브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2,"브라운", "2023-01-02", "11:00"));

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
