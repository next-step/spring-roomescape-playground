package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(new Reservation(1, "브라운", "2023-01-01", "10:00"));
        reservationList.add(new Reservation(2, "브라운", "2023-01-02", "11:00"));

        return ResponseEntity.status(HttpStatus.OK).body(reservationList);
    }
}
