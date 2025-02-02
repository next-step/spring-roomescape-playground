package roomescape.reservation.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.reservation.entity.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = createReservations();

    @GetMapping("/reservation")
    public String reservations(Model model) {
        model.addAttribute("reservations", reservations);
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }


    private List<Reservation> createReservations() {
        List<Reservation> createReservation = List.of(
                Reservation.ofTime(1, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
                Reservation.ofTime(2, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)),
                Reservation.ofTime(3,"브라운",LocalDate.of(2023,1,3),LocalTime.of(12,0))
        );
        return new ArrayList<>(createReservation);
    }
}
