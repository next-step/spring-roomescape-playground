package roomescape.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
        @Valid @RequestBody ReservationRequest reservationRequest) throws
        URISyntaxException {
        Long id = reservationService.createReservation(reservationRequest);
        ReservationResponse reservationResponse = reservationService.getReservation(id);
        return ResponseEntity.created(new URI("/reservations/" + reservationResponse.getId()))
            .body(reservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity cancelReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
