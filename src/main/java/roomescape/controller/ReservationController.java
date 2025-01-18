package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }

}
