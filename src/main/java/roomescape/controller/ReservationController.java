package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new Reservation( "브라운", "2025-01-01", "10:00"));
        reservations.add(new Reservation("코니", "2025-01-02", "11:00"));
    }

    @ResponseBody
    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return reservations.stream()
                .map((ReservationResponse::from))
                .toList();
    }


}
