package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

import java.util.List;

@Controller
public class RoomEscapeController {

    private final ReservationService reservationService;

    public RoomEscapeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String home(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation(
    ) {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @PostMapping("/reservations")
    public String postReservation(
            @RequestBody ReservationRequest request,
            HttpServletResponse response
    ) {
        reservationService.createReservation(request);

        response.setStatus(HttpServletResponse.SC_CREATED);
        return "redirect:/reservations";
    }
}
