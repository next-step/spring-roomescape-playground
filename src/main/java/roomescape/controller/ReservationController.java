package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations() {
        reservations.add(Reservation.toEntity(1, "브라운", "2023-01-01", "10:00"));
        reservations.add(Reservation.toEntity(2, "브라운", "2023-01-02", "11:00"));
        reservations.add(Reservation.toEntity(3, "브라운", "2023-01-03", "12:00"));

        return ResponseEntity.ok().body(reservations);
    }


}