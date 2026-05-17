package roomescape.reservation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.ReservationId;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@Validated
public class ReservationController {
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return service.getReservations();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody @Valid CreateReservationRequest body) {
        ReservationResponse result = service.createReservation(body);

        return ResponseEntity
                .created(URI.create("/reservations/" + result.id().id()))
                .body(result);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable long id) {
        ReservationId reservationId = new ReservationId(id);
        service.deleteReservation(reservationId);
    }
}
