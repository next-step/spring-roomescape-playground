package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest reservationRequest) {
        Reservation savedReservation = reservationService.createReservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.timeId());

        ReservationResponse response = ReservationResponse.from(savedReservation);
        return ResponseEntity.created(URI.create("/reservations/" + response.id())).body(response);
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> findAllReservations() {
        return reservationService.findAllReservations().stream().map(ReservationResponse::from).toList();
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
