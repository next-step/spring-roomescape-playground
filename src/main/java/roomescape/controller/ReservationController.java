package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        ReservationService.setReservations(reservations);
    }

    @ResponseBody
    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return reservations.stream()
                .map((ReservationResponse::from))
                .toList();
    }


}
