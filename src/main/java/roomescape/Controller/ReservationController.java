package roomescape.Controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationCreateForm;
import roomescape.dto.ReservationResponseForm;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "new-reservation";
    }


    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponseForm>> getReservations() {
        List<ReservationResponseForm> reservations = reservationService.getReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseForm> createReservation(
            @Valid @RequestBody ReservationCreateForm reservationRequest) {
        Long id = reservationService.createReservation(reservationRequest);
        ReservationResponseForm reservationResponse = reservationService.getReservation(id);
        return ResponseEntity.created(URI.create("/reservations/" + reservationResponse.getId()))
                .body(reservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}
