package roomescape.reservation.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.request.ReservationRequest;
import roomescape.reservation.response.ReservationResponse;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.service.ReservationValidator;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final ReservationService reservationService;
    private final ReservationValidator reservationValidator;

    public ReservationRestController(ReservationService reservationService,
        ReservationValidator reservationValidator) {
        this.reservationService = reservationService;
        this.reservationValidator = reservationValidator;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.ok(reservationService.getAllReservation());
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
        @RequestBody final ReservationRequest request
    ) {
        reservationValidator.validate(request);
        final ReservationResponse response = reservationService.create(request);

        return ResponseEntity
            .created(URI.create("/reservations/" + response.id()))
            .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable final Long id
    ) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
