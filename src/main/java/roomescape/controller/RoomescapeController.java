package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomescapeController {

    List<Reservation> reservations = new ArrayList<Reservation>();

    @GetMapping("/")
    public String welcomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> checkReservations(){
        reservations.add(new Reservation(1L,"hkj","2024","05-11"));
        return ResponseEntity.ok(reservations);
    }
}
