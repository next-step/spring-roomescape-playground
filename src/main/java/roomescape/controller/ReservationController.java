package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    @Autowired
    public ReservationController(ReservationService reservationService,
                                 ReservationRepository reservationRepository,
                                 TimeRepository timeRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> responses = reservationService.getAllReservations()
                .stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        Reservation savedReservation = reservationService.createReservation(request);
        URI location = URI.create("/reservations/" + savedReservation.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .body(ReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean deleted = reservationService.deleteReservation(id);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
