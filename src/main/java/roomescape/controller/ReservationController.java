package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservations() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservationService.showReservations();
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ReservationResponse createReservation(@RequestBody ReservationCreateRequest request) {
        return reservationService.reserve(request);
    }
}
