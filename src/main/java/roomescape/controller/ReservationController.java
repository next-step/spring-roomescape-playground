package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import roomescape.domain.Reservation;


@Controller
public class ReservationController {
    private List<Reservation> reservations = Arrays.asList(
            new Reservation(1, "브라운", "2023-01-01", "10:00"),
            new Reservation(2, "블루", "2023-02-01", "11:00"),
            new Reservation(3, "옐로우", "2023-05-01", "12:00")
    );

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations(){
        return ResponseEntity.ok(this.reservations);
    }
}
