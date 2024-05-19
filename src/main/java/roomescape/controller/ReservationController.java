package roomescape.controller;

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
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

@Controller
public class ReservationController {

    private final ReservationRepository repository;

    public ReservationController(final ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> getReservations() {
        return repository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> saveReservation(
            @RequestBody ReservationRequest request) {
        final Reservation reservation = request.convertReservation();
        final Reservation savedReservation = repository.save(reservation);
        final ReservationResponse response = ReservationResponse.from(savedReservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
