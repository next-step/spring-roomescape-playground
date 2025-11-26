package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.reservationDto.ReservationCreateRequest;
import roomescape.dto.reservationDto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    @Autowired
    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return service.getAllReservations()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationCreateRequest requestDto
            //@RequestHeader(value = "Idempotency-Key", required = true) String idempotencyKey
    ) {
        /*if(service.exitsKey(idempotencyKey)) {
            Reservation existingReservation = service.get(requestDto);
            ReservationResponse responseDto = ReservationResponse.from(existingReservation);

            return ResponseEntity.ok(responseDto);
        }
        Reservation savedReservation = service.addReservation(requestDto.toEntity(), idempotencyKey);*/
        Reservation savedReservation = service.addReservation(requestDto);

        ReservationResponse responseDto = ReservationResponse.from(savedReservation);
        URI location = URI.create("/reservations/" + savedReservation.getId());

        return ResponseEntity.created(location).body(responseDto);
    }
}
