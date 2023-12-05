package roomescape.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.ReservationCommandService;
import roomescape.application.ReservationQueryService;
import roomescape.application.dto.ReservationCreateRequest;
import roomescape.application.dto.ReservationResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;

    public ReservationController(final ReservationCommandService reservationCommandService, final ReservationQueryService reservationQueryService) {
        this.reservationCommandService = reservationCommandService;
        this.reservationQueryService = reservationQueryService;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservationQueryService.findReservations();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody final ReservationCreateRequest request) {
        final ReservationResponse response = reservationCommandService.addReservation(request);
        return ResponseEntity.created(URI.create("/reservations/" + response.getId()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable final Long id) {
        reservationCommandService.removeReservation(id);
        return ResponseEntity.noContent().build();
    }
}
