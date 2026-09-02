package roomescape;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time")
                )
        );
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody ReservationRequest request
    ) {

        Reservation reservation = reservationService.create(request);

        ReservationResponse response =
                ReservationResponse.from(reservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequest request
    ) {

        Reservation reservation =
                reservationService.update(id, request);

        return ResponseEntity.ok(
                ReservationResponse.from(reservation)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        reservationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
