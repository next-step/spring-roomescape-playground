package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public ReservationController(ReservationRepository reservationRepository, ReservationService reservationService) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> reservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                    .map(ReservationResponse::from)
                    .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.create(
                request.name(),
                request.date(),
                request.time()
        );

        ReservationResponse reservationResponse = ReservationResponse.from(reservation);

        URI uri = URI.create("/reservations/" + reservationResponse.id());

        return ResponseEntity
                .created(uri)
                .body(reservationResponse);
    }
}
