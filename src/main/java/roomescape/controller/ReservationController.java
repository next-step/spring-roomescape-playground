package roomescape.controller;

import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.ReservationList;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationRequest;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {

    private final ReservationList reservations;

    public ReservationController(ReservationList reservations) {
        this.reservations = reservations;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> findAll() {
        return reservations.findAll();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationRequest request) {
        ReservationResponse created = reservations.create(request);
        URI location = URI.create("/reservations/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservations.delete(id);
        return ResponseEntity.noContent().build();
    }
}
