package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
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
        return  service.getAllReservations().stream()
                .map(ReservationResponse::from) // (::from은 ReservationResponse::from과 동일)
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
    ) {

        Reservation reservationToCreate = new Reservation(
                requestDto.name(),
                requestDto.date(),
                requestDto.time()
        );

        Reservation savedReservation = service.addReservation(reservationToCreate);

        ReservationResponse responseDto = ReservationResponse.from(savedReservation);
        URI location = URI.create("/reservations/" + savedReservation.getId());

        return ResponseEntity.created(location).body(responseDto);
    }
}
