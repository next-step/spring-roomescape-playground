package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class RoomescapeController {

    private final ReservationService reservationService;

    public RoomescapeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservations = reservationService.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        ReservationResponse reservation = reservationService.create(request);
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.id()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
